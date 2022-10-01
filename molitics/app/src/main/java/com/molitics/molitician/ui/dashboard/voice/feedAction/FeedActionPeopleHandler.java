package com.molitics.molitician.ui.dashboard.voice.feedAction;


import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 03/04/18.
 */

public class FeedActionPeopleHandler implements FeedActionPresenter.FeedActionRequest {


    FeedActionPresenter.FeedActionUI mView;

   public FeedActionPeopleHandler(FeedActionPresenter.FeedActionUI mView) {
        this.mView = mView;
    }

    @Override
    public void getFeedActionList(int feed_id, int action, int total_count) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onFeedActionResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onFeedActionError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getFeedActionPeopleList(feed_id, action, total_count);
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
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getUserUnFollow(user_id);
        apiHandler.getData(apiResponseCall);
    }

}
