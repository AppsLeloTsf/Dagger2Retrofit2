package com.molitics.molitician.ui.dashboard.login;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.LoginRequest;
import com.molitics.molitician.ui.dashboard.login.interfacess.LoginPresenter;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by lenovo on 10/13/2016.
 */

public class LoginHandler implements LoginPresenter.UserRequest {

    LoginPresenter.LoginView mView;

    public LoginHandler(LoginPresenter.LoginView mView) {

        this.mView = mView;
    }


    @Override
    public void submitEditOtp(String otp) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onEditOtpResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onEditOtpApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onLoginServerException(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().submitEditOtp(otp);
        apiHandler.getData(apiResponseCall);
    }


    @Override
    public void setLoginDetail(LoginRequest loginRequest) {

        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onLoginResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLoginApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onLoginServerException(serverException);
            }


        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().userLogin(loginRequest);
        apiHandler.getData(request);
    }

    @Override
    public void setOtp(String url,OtpModel otp) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                //   mView.onLoginResponse(apiResponse);

                mView.onNumberResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onNumberException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().otpSubmit(url,otp);
        apiHandler.getData(request);
    }
}
