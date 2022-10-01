package com.molitics.molitician.ui.dashboard.register.register.viewModel

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.R
import com.molitics.molitician.model.Data
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel
import com.molitics.molitician.ui.dashboard.register.UserModel
import com.molitics.molitician.ui.dashboard.register.model.DeviceInfo
import com.molitics.molitician.ui.dashboard.register.model.SignInRequestModel
import com.molitics.molitician.ui.dashboard.register.model.User
import com.molitics.molitician.ui.dashboard.register.register.RegisterNavigation
import com.molitics.molitician.ui.dashboard.register.register.RegisterRepo
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.PrefUtil
import com.molitics.molitician.util.Util
import com.molitics.molitician.util.checkContactNumber
import com.molitics.molitician.util.isEmailValid
import com.molitics.molitician.util.resultFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val baseApplication: MolticsApplication,
    private val registerRepo: RegisterRepo
) : BaseViewModel<RegisterNavigation>() {

    val contactNumber = ObservableField<String>("")
    val userName = ObservableField<String>("")
    val userUniqueName = ObservableField<String>("")
    val userEmail = ObservableField<String>("")
    val userPassword = ObservableField<String>("")
    val uniqueUserNameValid = ObservableField<Boolean>(false)
    val userConfirmPassword = ObservableField<String>("")
    var genderRadioChecked = MutableLiveData<Int>()
    var userAuth = ""
    var gender = 0
    var dateOfBirth = ObservableField<String>("")
    private var stateId: Int? = 0
    var districtId: Int? = 0

    var locationObservableList: ObservableArrayList<ConstantModel> = ObservableArrayList()
    var districtObservableList: ObservableArrayList<ConstantModel> = ObservableArrayList()
    var locationList: LiveData<List<ConstantModel>> = MutableLiveData()
    var districtList: LiveData<List<ConstantModel>> = MutableLiveData()
    var muteRegisterData: MutableLiveData<UserModel> = MutableLiveData()
    var registerData: LiveData<UserModel> = muteRegisterData

    init {
        locationList = registerRepo.bindLocation()
        districtList = registerRepo.bindDistrictLocation()
    }

    fun setLocationData(data: List<ConstantModel>) {
        locationObservableList.addAll(data)
    }

    fun setDistrictLocationData(data: List<ConstantModel>) {
        districtObservableList.addAll(data)
    }

    fun getStateLocationList(stateId: Int?) {
        this.stateId = stateId
        viewModelScope.launch(coroutinException) {
            registerRepo.getStateLocation(stateId)
        }
    }

    fun checkUserName(uniqueName: String) {
        uniqueUserNameValid.set(false)
        viewModelScope.launch(coroutinException) {
            val data: Data? = registerRepo.checkUserNameApi(userAuth,User(username = uniqueName))
                .resultFactory(baseApplication)
            data?.let {
                uniqueUserNameValid.set(true)
            }
        }
    }

    /**
     *  UI click - submit contact
     */
    fun onNumberNextClick() {
        if (checkContactNumber(baseApplication,contactNumber.get()!!)) {
            viewModelScope.launch(coroutinException) {
                val userDetails = SignInRequestModel(
                    device = getDeviceInfo(),
                    mobile = contactNumber.get(), verification_for = Constant.From.SIGN_UP_FRAGMENT
                )

                val data: Data? =
                    registerRepo.registerNumberService(userDetails).resultFactory(baseApplication)
                data?.let {
                    userAuth = it.userModel.auth_key
                    navigator.navigateToOtp(contactNumber.get(), it.userModel.auth_key)
                }
            }
        }
    }

    /** Ui click
     * create account */
    fun doneButtonClicked() {
        viewModelScope.launch(coroutinException) {
            val userModel = User(
                contactNumber.get(), 0, userPassword.get(), birth_date = dateOfBirth.get(),
                password_confirmation = userConfirmPassword.get(), email = userEmail.get(),
                name = userName.get(), username = userUniqueName.get(), gender = gender,
                state = stateId, district = districtId
            )

            val userDetails =
                SignInRequestModel(userModel, getDeviceInfo(), verification_for = "account")
            val data: Data? = registerRepo.submitRegisterData(userDetails, userAuth)
                .resultFactory(baseApplication)
            data?.let {
                muteRegisterData.postValue(null)
            }
        }
    }

    private fun getDeviceInfo(): DeviceInfo {
        val fcmToken = PrefUtil.getString(Constant.PreferenceKey.FCM_TOKEN)
        val deviceId = Util.getDeviceIdRegisteredOnServer(baseApplication)
        return DeviceInfo("android", "4.2.17", deviceId, fcmToken)
    }

    fun onRegisterNextClick() {
        if (validateForm()) {
            navigator.navigateToStateLocation()
        }
    }

    private fun validateForm(): Boolean {
        if (userName.get().isNullOrEmpty()) {
            Util.toastShort(
                baseApplication,
                baseApplication.getString(R.string.user_name_field_cannt_be_empty)
            )
            return false
        }
        if (uniqueUserNameValid.get() == false) {
            Util.toastShort(
                baseApplication,
                baseApplication.getString(R.string.user_name_not_available)
            )
            return false
        }
        if (userPassword.get()?.length!! < 9) {
            Util.toastShort(
                baseApplication,
                baseApplication.getString(R.string.password_must_be_eight_char)
            )
            return false
        }
        if (userPassword.get() != userConfirmPassword.get()) {
            Util.toastShort(
                baseApplication,
                baseApplication.getString(R.string.password_is_not_same)
            )
            return false
        }
        if (gender == 0) {
            Util.toastShort(baseApplication, baseApplication.getString(R.string.select_gender))
            return false
        }
        if (!isEmailValid(userEmail.get())) {
            Util.toastShort(baseApplication, baseApplication.getString(R.string.email_invalid))
            return false
        }
        return true
    }

    private val coroutinException = CoroutineExceptionHandler { coroutineContext, throwable ->
        Util.showLog("RegisterViewModel", throwable.message)
    }

}