package com.molitics.molitician.ui.dashboard.register.viewModel

import android.content.IntentFilter
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.R
import com.molitics.molitician.model.Data
import com.molitics.molitician.ui.dashboard.register.SignInNavigation
import com.molitics.molitician.ui.dashboard.register.UserModel
import com.molitics.molitician.ui.dashboard.register.location.repo.SignInRepo
import com.molitics.molitician.ui.dashboard.register.model.DeviceInfo
import com.molitics.molitician.ui.dashboard.register.model.SignInRequestModel
import com.molitics.molitician.ui.dashboard.register.model.User
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.PrefUtil
import com.molitics.molitician.util.Util
import com.molitics.molitician.util.checkContactNumber
import com.molitics.molitician.util.resultFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import com.facebook.FacebookSdk.getApplicationContext

import com.google.android.gms.auth.api.phone.SmsRetriever

import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.molitics.molitician.util.Util.showToast


class SignInViewModel(
    private val baseApplication: MolticsApplication,
    private val signInRepo: SignInRepo
) : BaseViewModel<SignInNavigation>() {



    val contactNumber = ObservableField<String>("")
    val userPassword = ObservableField<String>("")
    val otp = ObservableField<String>("")
    private var localContactResponse: MutableLiveData<UserModel> = MutableLiveData()
    var contactResponse: LiveData<UserModel> = localContactResponse
    var userAuth = ""
    private lateinit var screenType: String

    fun setScreenType(value: String) {
        screenType = value
    }

    fun signInClick() {
        if (checkContactNumber(baseApplication, contactNumber.get()!!)) {
            navigator.navigateToPassword()
        }
    }

    /** UI - login click
     */
    fun onOtpClick() {
        if (checkContactNumber(baseApplication, contactNumber.get()!!))
        {
            viewModelScope.launch(coroutinException) {
                val userDetails = SignInRequestModel(
                    device = getDeviceInfo(),
                    mobile = contactNumber.get(), verification_for = Constant.From.SIGN_IN
                )
                val data: Data? =
                    signInRepo.sentOtpService(userDetails).resultFactory(baseApplication)
                data?.userModel?.let {
                    userAuth = it.auth_key
                    navigator.navigateToOtp(contactNumber.get(), it.auth_key)
                }
            }

        }
     // startSMSListener()

    }

    /**UI - login click
     * source 1  = password, 2 = otp
     */
    fun onPasswordVerifyClick() {
        if (validatePasswordForm()) {
            singInApi(
                SignInRequestModel(
                    User(contactNumber.get(), password = userPassword.get(), source = 1)
                )
            )
        }
    }
    private fun validatePasswordForm(): Boolean {
        if (userPassword.get().isNullOrEmpty()) {
            showToast(baseApplication, baseApplication.getString(R.string.enter_password))
            return false
        }
        return true
    }

    /** verify otp click */
    fun onOtpVerifyClick() {
        when {
            otp.get().isNullOrEmpty() -> {
               showToast(baseApplication, baseApplication.getString(R.string.otp_not_enter))
            }
            screenType == Constant.From.SIGN_UP_FRAGMENT -> {
                verifyOtpApi(

                    SignInRequestModel(
                            mobile = contactNumber.get(),
                            otp = otp.get(),
                            verification_for = Constant.From.SIGN_UP_FRAGMENT,
                    )
                )
            }

            else -> {
                singInApi(
                        SignInRequestModel(
                                User(
                                        contactNumber.get(),
                                        password = otp.get(),
                                        otp = otp.get(),
                                        source = 2
                                )
                        )
                )
            }
        }
    }

    /** verify otp click*/
    private fun verifyOtpApi(userDetails: SignInRequestModel) {
        viewModelScope.launch(coroutinException) {
            val data = signInRepo.verifyApiService(userAuth, userDetails).resultFactory(baseApplication)
            data?.let {
                navigator.navigateToOtp(null, null)
            }
        }
    }

    /** resend otp click */
    fun resendOtp() {
        val userDetails = SignInRequestModel(
            device = getDeviceInfo(), mobile = contactNumber.get(),
            verification_for = screenType
        )
        viewModelScope.launch(coroutinException) {
            signInRepo.resendOtpService(userDetails).resultFactory(baseApplication)
//            data?.let {
//                navigator.navigateToOtp(contactNumber.get(), it.userModel.auth_key)
//            }
        }
    }

    private fun singInApi(userDetails: SignInRequestModel) {
        setIsLoading(true)
        viewModelScope.launch(coroutinException) {
            userDetails.device = getDeviceInfo()
            Log.d("TSF_APPS","Mobile: "+contactNumber.get() )
            Log.d("TSF_APPS","Screen: "+screenType )

            val data =
                signInRepo.postSignInRequest(userAuth, userDetails).resultFactory(baseApplication)
            setIsLoading(false)
            data?.userModel?.let {
                it.mobile = userDetails.user?.mobile ?: ""
                localContactResponse.postValue(it)
            }
        }
    }

    private fun getDeviceInfo(): DeviceInfo {
        val fcmToken = PrefUtil.getString(Constant.PreferenceKey.FCM_TOKEN)
        val deviceId = Util.getDeviceIdRegisteredOnServer(baseApplication)
        return DeviceInfo("android", "4.2.17", deviceId, fcmToken)
    }

    private val coroutinException = CoroutineExceptionHandler { coroutineContext, throwable ->
        setIsLoading(false)
        Util.showLog("coroutinException", throwable.message)
    }


}