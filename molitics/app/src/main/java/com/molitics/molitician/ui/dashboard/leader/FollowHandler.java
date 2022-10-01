package com.molitics.molitician.ui.dashboard.leader;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 6/20/2017.
 */

public class FollowHandler implements FollowPresenter.FollowRequest {

    FollowPresenter.FollowView mView;

    public FollowHandler(FollowPresenter.FollowView mView) {
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
                mView.onFollowApiException(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
                //   mView.onLeaderFormValidation(formValidation);
            }

            @Override
            public void onServerError(ServerException serverException) {

                mView.onFollowServerError(serverException);
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
                mView.onUnFollowRespnse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onFollowApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onFollowServerError(serverException);
            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().unFollowCandidate(candidate_id);
        apiHandler.getData(request);
    }

    @Override
    public void onLeaderAction(int candidate_id, int action) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onLeaderActionResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLeaderActionApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().onLeaderAction(candidate_id, action);
        apiHandler.getData(responseCall);
    }

    @Override
    public void onDeleteLeaderAction(int candidate_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onDeleteLeaderAction(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onDeleteLeaderActionApiError(apiException);
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
