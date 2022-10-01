package com.molitics.molitician.ui.dashboard.userProfile.issue_following;


import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 11/12/17.
 */

public class IssueFollowingHandler implements IssueFollowingPresenter.IssueFollowingRequest {


    IssueFollowingPresenter.IssueFollowingUi mView;

    IssueFollowingHandler(IssueFollowingPresenter.IssueFollowingUi mView) {
        this.mView = mView;
    }


    @Override
    public void getFollowingIssue(int count) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onIssueFollowingResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onIssueFollowingError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getIssueFollowing(count);

        apiHandler.getData(apiResponseCall);

    }


    @Override
    public void likeDislike(int issue_id, int action) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onLikeDislikeResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLikeDislikeError(apiException);
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
                mView.onUnFollowResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onUnFollowError(apiException);
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
                mView.onDeleteIssueResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onDeleteIssueError(apiException);
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
                mView.onFollowResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onFollowError(apiException);
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
