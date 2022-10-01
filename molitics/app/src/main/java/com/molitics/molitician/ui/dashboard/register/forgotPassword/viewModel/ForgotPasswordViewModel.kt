package com.molitics.molitician.ui.dashboard.register.forgotPassword.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.ui.dashboard.register.forgotPassword.ForgotRepo
import com.molitics.molitician.ui.dashboard.register.model.DeviceInfo
import com.molitics.molitician.ui.dashboard.register.model.SignInRequestModel
import com.molitics.molitician.ui.dashboard.register.model.User
import com.molitics.molitician.ui.dashboard.register.register.RegisterNavigation
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.PrefUtil
import com.molitics.molitician.util.Util
import com.molitics.molitician.util.checkContactNumber
import com.molitics.molitician.util.resultFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val baseApplication: MolticsApplication,
    private val forgotRepo: ForgotRepo
) : BaseViewModel<RegisterNavigation>() {

    val contactNumber = ObservableField<String>("")
    val newPassword = ObservableField("")

    val reNewPassword = ObservableField("")

    var auth = ""
    private lateinit var screenType: String

    fun setScreenType(value: String) {
        screenType = value
    }

    /** UI click- enter contact number and submit*/
    fun onForgotNextClick() {
        if (checkContactNumber(baseApplication, contactNumber.get()!!)) {
            val userDetails = SignInRequestModel(
                device = getDeviceInfo(), mobile = contactNumber.get(),
                verification_for = Constant.From.FORGET_PASSWORD
            )
            viewModelScope.launch(coroutinException) {
                val data = forgotRepo.resendOtpService(userDetails).resultFactory(baseApplication)
                data?.let {
                    auth = it.userModel.auth_key
                    navigator.navigateToOtp(contactNumber.get(),auth)
                }
            }
        }
    }

    /** UI click- submit new password*/
    fun submitNewPasswordOnServer() {
        viewModelScope.launch(coroutinException) {
            val userDetails = SignInRequestModel(
                User(
                    password = newPassword.get(),
                    password_confirmation = reNewPassword.get()
                ),
                verification_for = Constant.From.FORGET_PASSWORD
            )

            val data = forgotRepo.forgotRestPassword(auth,userDetails).resultFactory(baseApplication)
            data?.let {
                navigator.navigateToOtp()
            }
        }
    }

    private fun getDeviceInfo(): DeviceInfo {
        val fcmToken = PrefUtil.getString(Constant.PreferenceKey.FCM_TOKEN)
        val deviceId = Util.getDeviceIdRegisteredOnServer(baseApplication)
        return DeviceInfo("android", "4.2.17", deviceId, fcmToken)
    }

    private val coroutinException = CoroutineExceptionHandler { coroutineContext, throwable ->
        Util.showLog("ForgotPasswordViewModel", throwable.message)
    }
}