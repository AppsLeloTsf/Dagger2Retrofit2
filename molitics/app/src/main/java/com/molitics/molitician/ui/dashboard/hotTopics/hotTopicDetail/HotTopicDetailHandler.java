package com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail;


import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 28/11/17.
 */

public class HotTopicDetailHandler implements HotTopicDetailPresenter.HotTopicDetailRequest {

    private HotTopicDetailPresenter.HotTTopicDetailUI mView;

    public HotTopicDetailHandler(HotTopicDetailPresenter.HotTTopicDetailUI mView) {

        this.mView = mView;
    }

    @Override
    public void getHotTopicDetail(int topic_id, int count) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onHotTopicDetailResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onHotTopicDetailError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getHotTopicDetail(topic_id, count);
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
