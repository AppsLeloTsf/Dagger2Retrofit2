package com.molitics.molitician.ui.dashboard.election.upcoming_election.upcomig_parties;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 1/12/2017.
 */

public class UpComingPartyHandler implements UpComingPartyPresenter.UpComingRequest {

    UpComingPartyPresenter.UpComingView mView;

    public UpComingPartyHandler(UpComingPartyPresenter.UpComingView mView) {
        this.mView = mView;
    }

    @Override
    public void getPartyList(int state_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onPartyResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onPartyApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onPartyServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getUpcomingPartyList(state_id);
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
                mView.onPartyServerError(serverException);

            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getConstituencyList(state_id);
        apiHandler.getData(apiResponseCall);
    }

}
