package com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by Rahul on 3/10/2017.
 */

public class LeaderExtraHandler implements LeaderExtraPresenter.LeaderExtraRequest {

    LeaderExtraPresenter.LeaderExtraView mView;

    public LeaderExtraHandler(LeaderExtraPresenter.LeaderExtraView mView) {
        this.mView = mView;
    }

    @Override
    public void followCandidate(int candidate_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onFollowResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }

        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().followCandidate(candidate_id);
        apiHandler.getData(request);

    }

    @Override
    public void unFollowCandidate(int candidate_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onUnFollowresponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
            }
        });
        Call<APIResponse> request = RetrofitRestClient.getInstance().unFollowCandidate(candidate_id);
        apiHandler.getData(request);
    }

    @Override
    public void onNewsLeaderAction(String type, int news_id, int candidae_id, int action) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onNewsLeaderActionResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onNewsLeaderActionApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().onLeaderAction(candidae_id, action);
        apiHandler.getData(responseCall);
    }

    @Override
    public void onDeleteNewsLeaderAction(String type_url, int news_id, int candidate_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onDeleteNewsLeaderAction(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onDeleteNewsLeaderActionApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().onDeleteLeaderAction(candidate_id);
        apiHandler.getData(responseCall);
    }
}
