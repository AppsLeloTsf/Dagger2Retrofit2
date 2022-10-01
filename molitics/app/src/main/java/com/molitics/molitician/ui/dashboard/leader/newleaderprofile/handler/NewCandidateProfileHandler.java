package com.molitics.molitician.ui.dashboard.leader.newleaderprofile.handler;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 6/28/2017.
 */

public class NewCandidateProfileHandler implements NewCandidateProfilePresenter.UserRequest,
        NewCandidateProfilePresenter.VoiceRequest {

    NewCandidateProfilePresenter.LeaderProfileView mView;
    NewCandidateProfilePresenter.VoiceUI voiceView;
    NewCandidateProfilePresenter.VoiceActionUi voiceActionUi;

    public NewCandidateProfileHandler(NewCandidateProfilePresenter.LeaderProfileView mView,
                                      NewCandidateProfilePresenter.VoiceUI voiceView,
                                      NewCandidateProfilePresenter.VoiceActionUi voiceActionUi) {
        this.mView = mView;
        this.voiceView = voiceView;
        this.voiceActionUi = voiceActionUi;
    }

    public NewCandidateProfileHandler(NewCandidateProfilePresenter.LeaderProfileView mView) {
        this.mView = mView;
    }

    @Override
    public void getProfile(int leader_id) {
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

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().getProfileData(leader_id);
        apiHandler.getData(responseCall);
    }

    @Override
    public void getAllVoice(int trending, int state_id, int page, String device_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                voiceView.onVoiceResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                voiceView.onVoiceApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().getAllVoice(trending, state_id, page, device_id);
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
                voiceView.onUserImageResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                voiceView.onUserImageError(apiException);
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

    @Override
    public void getMoreLeaderFeeds(int leader_id, int total_count,String device_id) {


        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onMoreLeaderFeedsResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onMoreLeaderFeedsError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().getLeaderFeeds(leader_id, total_count,device_id);
        apiHandler.getData(responseCall);
    }


}
