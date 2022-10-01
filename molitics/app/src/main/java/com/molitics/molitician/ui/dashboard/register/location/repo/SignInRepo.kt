package com.molitics.molitician.ui.dashboard.register.location.repo

import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.httpapi.awaitResult
import com.molitics.molitician.ui.dashboard.register.model.SignInRequestModel

class SignInRepo {

    suspend fun postSignInRequest(authKey: String, userDetails: SignInRequestModel) = RetrofitRestClient.instance.postSignIn(authKey, userDetails).awaitResult()

    suspend fun sentOtpService(user: SignInRequestModel) = RetrofitRestClient.instance.sendOtp(user).awaitResult()

    suspend fun resendOtpService(userDetails: SignInRequestModel) = RetrofitRestClient.instance.sendOtp(userDetails).awaitResult()

    suspend fun verifyApiService(userDetails: SignInRequestModel, auth: String) = RetrofitRestClient.instance.verifyOtp(auth, userDetails).awaitResult()

}