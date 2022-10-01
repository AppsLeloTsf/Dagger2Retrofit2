package com.molitics.molitician.ui.dashboard.leader.newleaderprofile.leaderNews;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 7/1/2017.
 */

public class LeaderNewsHandler implements LeaderNewsPresenter.LeaderNewsRequest {
    LeaderNewsPresenter.LeaderNewsView mView;

    public LeaderNewsHandler(LeaderNewsPresenter.LeaderNewsView mView) {
        this.mView = mView;

    }

    @Override
    public void getLeaderNews(int page_no, int leader_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onNewsResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onNewsApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().getLeaderNews(page_no, leader_id);
        apiHandler.getData(responseCall);
    }

    @Override
    public void seekNews(int candidate_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onNewsSeekResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onNewsSeekException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().seekNews(candidate_id);
        apiHandler.getData(responseCall);
    }


}
