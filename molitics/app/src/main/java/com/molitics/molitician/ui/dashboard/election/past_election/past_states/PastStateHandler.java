package com.molitics.molitician.ui.dashboard.election.past_election.past_states;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 1/10/2017.
 */

public class PastStateHandler implements PastStatePresenter.PastStateRequest {
    PastStatePresenter.PastStateView mView;

    public PastStateHandler(PastStatePresenter.PastStateView mView) {
        this.mView = mView;
    }

    @Override
    public void getStateData(int state_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onStateResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onStateApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onStateServerError(serverException);

            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getPastState(state_id);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void getConstituencyList(int state_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onConstituencyResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onConstituencyApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onStateServerError(serverException);

            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getConstituencyList(state_id);
        apiHandler.getData(apiResponseCall);
    }

}
