package com.molitics.molitician.ui.dashboard.userProfile.editContact;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 1/16/2017.
 */

public class EditContactHandler implements EditNumberPresenter.EditContactRequest {

    EditNumberPresenter.EditContactView mView;

    public EditContactHandler(EditNumberPresenter.EditContactView mView) {
        this.mView = mView;
    }

    @Override
    public void submitEditContact(String contact) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onContactResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onContactApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onContactServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().submitEditContact(contact);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void submitEditOtp(String otp) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onOtpResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onOtpApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onContactServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().submitEditOtp(otp);
        apiHandler.getData(apiResponseCall);
    }
}
