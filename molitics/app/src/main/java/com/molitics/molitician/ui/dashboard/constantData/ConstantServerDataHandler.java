package com.molitics.molitician.ui.dashboard.constantData;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 12/15/2016.
 */

public class ConstantServerDataHandler implements ConstantServerDataPresenter.UserRequest {

    ConstantServerDataPresenter.ConstantData mView;

    public ConstantServerDataHandler(ConstantServerDataPresenter.ConstantData mView) {
        this.mView = mView;
    }

    @Override
    public void getStateList() {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onStateResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onStateApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onStateServerException(serverException);
            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getStateList();
        apiHandler.getData(request);

    }

    @Override
    public void getActiveFragment() {
        final ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onActiveFragmentResponse(apiResponse.getData());
            }

            @Override
            public void onApiError(ApiException apiException) {

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onStateServerException(serverException);
            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getActiveFragment();
        apiHandler.getData(request);
    }
}
