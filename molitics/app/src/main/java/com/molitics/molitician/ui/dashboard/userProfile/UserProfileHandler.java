package com.molitics.molitician.ui.dashboard.userProfile;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 1/13/2017.
 */

public class UserProfileHandler implements UserProfilePresenter.UserProfileRequest {

    UserProfilePresenter.UserProfileView mView;

    public UserProfileHandler(UserProfilePresenter.UserProfileView mView) {

        this.mView = mView;

    }

    @Override
    public void getProfileData(int user_id, int count,String deviceId) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onProfileDataResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onProfileDataApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onProfileDataServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getUserProfileData(user_id, count,deviceId);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void getProfile() {

        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onProfileResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onProfileApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onProfileServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getProfile();
        apiHandler.getData(apiResponseCall);

    }

    @Override
    public void userFollow(int user_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onUserFollowResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onUserFollowError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onProfileServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getUserFollow(user_id);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void userUnFollow(int user_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onUserUnFollowResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onUserUnFollowError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onProfileServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getUserUnFollow(user_id);
        apiHandler.getData(apiResponseCall);
    }

}
