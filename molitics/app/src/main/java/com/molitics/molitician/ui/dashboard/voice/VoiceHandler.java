package com.molitics.molitician.ui.dashboard.voice;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 13/11/17.
 */

public class VoiceHandler implements VoicePresenter.VoiceRequest {
    VoicePresenter.VoiceUI mView;
    VoicePresenter.VoiceActionUi voiceActionUi;

    public VoiceHandler(VoicePresenter.VoiceUI mView, VoicePresenter.VoiceActionUi voiceActionUi) {
        this.mView = mView;
        this.voiceActionUi = voiceActionUi;
    }

    public VoiceHandler(VoicePresenter.VoiceActionUi voiceActionUi) {
        this.voiceActionUi = voiceActionUi;
    }

    @Override
    public void getAllVoice(int isGetTrending, int state_id, int page, String device_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onVoiceResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onVoiceApiError(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().getTypeAllVoice(isGetTrending, state_id, page, device_id);
        apiHandler.getData(responseCall);
    }

    @Override
    public void likeDislike(int issue_id, int action) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                voiceActionUi.onLikeDislikeResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                voiceActionUi.onLikeDislikeError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().likeDislikeIssue(issue_id, action);
        apiHandler.getData(responseCall);
    }


    @Override
    public void onUnFollow(int issue_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                voiceActionUi.onUnFollowResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                voiceActionUi.onUnFollowError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().deleteFollow(issue_id);
        apiHandler.getData(responseCall);
    }

    @Override
    public void deleteIssue(int issue_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                voiceActionUi.onDeleteIssueResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                voiceActionUi.onDeleteIssueError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().deleteIssue(issue_id);
        apiHandler.getData(responseCall);
    }

    @Override
    public void getUserImage(int user_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onUserImageResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {

                mView.onUserImageError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().getUserImage(user_id);
        apiHandler.getData(responseCall);
    }

    @Override
    public void onFollow(int issue_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                voiceActionUi.onFollowResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                voiceActionUi.onFollowError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().feedFollow(issue_id);
        apiHandler.getData(responseCall);
    }
}
