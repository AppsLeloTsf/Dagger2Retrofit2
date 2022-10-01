package com.molitics.molitician.ui.dashboard.login;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.DeviceRegistration;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 12/15/2016.
 */

public class LocationSelectionHandler implements LocationSelectionPresenter.UserRequest {

    LocationSelectionPresenter.LocationView mView;


    LocationSelectionHandler(LocationSelectionPresenter.LocationView mView) {
        this.mView = mView;
    }

    @Override
    public void hitStateList() {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onState(apiResponse.getData().getState_list());
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLocationSelectionApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onLocationSelectionServerException(serverException);
            }
        });
        Call<APIResponse> request = RetrofitRestClient.getInstance().getStateList();

        apiHandler.getData(request);

    }

    @Override
    public void getAllLocationFromState(int state_id) {

        final ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onStateSelection(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLocationSelectionApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onLocationSelectionServerException(serverException);
            }
        });
        Call<APIResponse> request = RetrofitRestClient.getInstance().getLocationFromList(state_id);
        apiHandler.getData(request);
    }

    @Override
    public void submitLocation(DeviceRegistration locationModel) {

        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onLocationSelectionResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLocationSelectionApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onLocationSelectionServerException(serverException);
            }
        });
        Call<APIResponse> request = RetrofitRestClient.getInstance().submitLocation(locationModel);
        apiHandler.getData(request);
    }
}
