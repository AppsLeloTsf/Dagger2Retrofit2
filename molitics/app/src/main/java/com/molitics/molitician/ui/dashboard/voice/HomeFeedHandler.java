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

public class HomeFeedHandler implements HomeFeedPresenter.FeedRequest {
    HomeFeedPresenter.HomeFeedUI mView;
    HomeFeedPresenter.HomeFeedActionUi homeFeedActionUi;

    public HomeFeedHandler(HomeFeedPresenter.HomeFeedUI mView, HomeFeedPresenter.HomeFeedActionUi feedActionUi) {
        this.mView = mView;
        this.homeFeedActionUi = feedActionUi;
    }

    public HomeFeedHandler(HomeFeedPresenter.HomeFeedActionUi feedActionUi) {
        this.homeFeedActionUi = feedActionUi;
    }

    @Override
    public void getAllFeeds(int state_id, int page) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onFeedResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onFeedApiError(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().getHomeFeedList(page, state_id);
        apiHandler.getData(responseCall);
    }

    @Override
    public void likeDislike(int issue_id, int action) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                homeFeedActionUi.onLikeDislikeResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                homeFeedActionUi.onLikeDislikeError(apiException);
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
                homeFeedActionUi.onUnFollowResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                homeFeedActionUi.onUnFollowError(apiException);
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
                homeFeedActionUi.onDeleteIssueResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                homeFeedActionUi.onDeleteIssueError(apiException);
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
                homeFeedActionUi.onFollowResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                homeFeedActionUi.onFollowError(apiException);
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
