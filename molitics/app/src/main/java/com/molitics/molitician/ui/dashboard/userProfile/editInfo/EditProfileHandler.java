package com.molitics.molitician.ui.dashboard.userProfile.editInfo;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.userProfile.model.EditUserProfileModel;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * Created by rahul on 1/13/2017.
 */

public class EditProfileHandler implements EditInfoPresenter.EditInfoRequest {
    EditInfoPresenter.EditInfoView mView;

    public EditProfileHandler(EditInfoPresenter.EditInfoView mView) {
        this.mView = mView;
    }

    @Override
    public void getStateList() {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onStateListResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onSubmitApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onSubmitServerError(serverException);
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
                mView.onStateListApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onSubmitServerError(serverException);
            }
        });
        Call<APIResponse> request = RetrofitRestClient.getInstance().getLocationFromList(state_id);
        apiHandler.getData(request);
    }

    @Override
    public void submitInfo(EditUserProfileModel submitEditProfile) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onSubmitResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onSubmitApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onSubmitServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().submitEditProfile(submitEditProfile);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void submitProfileImage(MultipartBody.Part image_url) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onProfileImageResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onProfileImageError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onSubmitServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().submitProfileImage(image_url);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void deleteProfileImage() {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onProfileImageDeleteResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onProfileImageDeleteError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onSubmitServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().deleteProfileImage();
        apiHandler.getData(apiResponseCall);
    }
}
