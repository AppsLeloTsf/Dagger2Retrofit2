package com.molitics.molitician.ui.dashboard.register.forgotPassword

import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.httpapi.awaitResult
import com.molitics.molitician.ui.dashboard.register.model.SignInRequestModel
import com.molitics.molitician.ui.dashboard.register.model.User

class ForgotRepo {

    suspend fun forgotPasswordService(userDetails: User) =
        RetrofitRestClient.instance.forgotPassword(userDetails).awaitResult()

    suspend fun resendOtpService(userDetails: SignInRequestModel) =
        RetrofitRestClient.instance.sendOtp(userDetails).awaitResult()

    suspend fun forgotRestPassword(authKey: String, userDetails: SignInRequestModel) =
        RetrofitRestClient.instance.forgotRestPassword(authKey, userDetails).awaitResult()
}