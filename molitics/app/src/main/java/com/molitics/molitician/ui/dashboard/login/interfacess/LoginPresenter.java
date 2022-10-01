package com.molitics.molitician.ui.dashboard.login.interfacess;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.LoginRequest;
import com.molitics.molitician.ui.dashboard.login.OtpModel;

import java.util.Map;

/**
 * Created by rahul on 10/13/2016.
 */

public class LoginPresenter {

    public interface LoginView {

        void onNumberResponse(APIResponse loginResponse);

        void onNumberException(ApiException apiException);

        void onEditOtpResponse(APIResponse apiResponse);

        void onEditOtpApiException(ApiException apiException);

        void onLoginResponse(APIResponse loginResponse);

        void onLoginApiException(ApiException apiException);

        void onLoginServerException(ServerException serverException);

        default void onLoginFormValidation(Map<String, String> errors) {

        }
    }

    public interface UserRequest {

        void setLoginDetail(LoginRequest loginDetail);

        void setOtp(String url, OtpModel otp);

        void submitEditOtp(String otp);
    }

}
