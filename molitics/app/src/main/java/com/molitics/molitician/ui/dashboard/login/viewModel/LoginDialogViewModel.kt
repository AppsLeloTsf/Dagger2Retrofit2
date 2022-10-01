package com.molitics.molitician.ui.dashboard.login.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.database.Database
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.model.APIResponse
import com.molitics.molitician.model.DeviceRegistration
import com.molitics.molitician.model.LoginRequest
import com.molitics.molitician.ui.dashboard.login.interfacess.UserLoginInterface
import com.molitics.molitician.ui.dashboard.login.repo.LoginDialogRepo
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.DeviceInfo
import com.molitics.molitician.util.PrefUtil
import com.molitics.molitician.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject

class LoginDialogViewModel : BaseViewModel<UserLoginInterface>() {
    val TAG = "LoginDialogViewModel"

    private var repository: LoginDialogRepo = LoginDialogRepo()

    var loginRequestModel: LoginRequest = LoginRequest()

    val deviceRegistration: DeviceRegistration = DeviceRegistration()


    @SuppressLint("CheckResult")
    fun loginUserRequest() {
        setIsLoading(true)
        compositeDisposable.add(RetrofitRestClient.instance?.userLoginRx(loginRequestModel)?.flatMap {
            setLoginData(it)
            deviceRegistration.state = PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_STATE_VALUE)
            RetrofitRestClient.instance?.submitLocationRx(deviceRegistration)
        }
                !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    Util.saveUserPreference(deviceRegistration)

                    navigator.onSuccessLogin()
                }, { error ->
                    setIsLoading(false)
                    Util.showLog(TAG, error.message)
                }))
    }

    @SuppressLint("CheckResult")
    fun signUpUserRequest() {
        setIsLoading(true)
        compositeDisposable.add(RetrofitRestClient.instance?.userLoginRx(loginRequestModel)!!
                !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    navigator.onSignUpResponse(loginRequestModel.mobile)

                }, { error ->
                    setIsLoading(false)
                    Util.showLog(TAG, error.message)
                }))
    }


    private fun setLoginData(loginResponse: APIResponse) {
        val getLoginResponse = loginResponse.data.deviceRegistration
        PrefUtil.putInt(Constant.PreferenceKey.NOTIFICATION_ACTIVE, 1)
        Database.deviceRegistration(getLoginResponse)
        Util.saveUserInfo(getLoginResponse)
    }

    fun handleFbSignIn(context: Context, data: String) {
        try {
            var pictureUrl = ""
            val jsonObject = JSONObject(data)

            val pictureObject = jsonObject.getJSONObject("picture")
            if (pictureObject != null) {
                val pictureDataObject = pictureObject.getJSONObject("data")
                if (pictureDataObject != null)
                    pictureUrl = pictureDataObject.optString("url")
            }
            val loginRequest = loginRequestModel
            loginRequest.id = jsonObject.optString("id")
            loginRequest.source = 2
            loginRequest.personName = jsonObject.optString("name")
            loginRequest.personEmail = jsonObject.optString("email")
            loginRequest.ageRange = jsonObject.optString("age_range")
            loginRequest.gender = jsonObject.optString("gender")
            loginRequest.image = pictureUrl
            loginRequest.deviceId = Util.getUnicDeviceId(context)
            DeviceInfo.getDeviceInfo(context as Activity, loginRequest)
            loginUserRequest()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun handleGoogleResult(context: Context, result: GoogleSignInResult) {
        /// result.status

        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount

            val loginRequest = loginRequestModel
            loginRequest.personEmail = acct?.email
            loginRequest.personPhoto = ""
            loginRequest.givenName = acct?.givenName
            loginRequest.personName = acct?.displayName
            loginRequest.source = 3
            loginRequest.appVersion = Util.getAppVersion(context)!!.versionName
            loginRequest.token = PrefUtil.getString(Constant.PreferenceKey.FCM_TOKEN)
            loginRequest.deviceId = Util.getUnicDeviceId(context)
            loginRequest.id = acct?.id
            DeviceInfo.getDeviceInfo(context as Activity, loginRequest)

            loginUserRequest()
        }
    }

    fun handleSignUp(context: Context, stringMobileNumberView: String) {
        val loginRequest = loginRequestModel
        loginRequest.mobile = stringMobileNumberView
        loginRequest.source = 1
        loginRequest.type = "android"
        loginRequest.appVersion = Util.getAppVersion(context).versionName
        loginRequest.token = PrefUtil.getString(Constant.PreferenceKey.FCM_TOKEN)
        loginRequest.deviceId = Util.getUnicDeviceId(context)
        DeviceInfo.getDeviceInfo(context as Activity, loginRequest)
        loginUserRequest()

    }
}