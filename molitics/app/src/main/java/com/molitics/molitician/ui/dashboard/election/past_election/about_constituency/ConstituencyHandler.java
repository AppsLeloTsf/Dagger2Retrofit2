package com.molitics.molitician.ui.dashboard.election.past_election.about_constituency;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 1/11/2017.
 */

public class ConstituencyHandler implements ConstituencyPresenter.ConstituencyUserRequest {

    ConstituencyPresenter.ConstituencyView mView;

    public ConstituencyHandler(ConstituencyPresenter.ConstituencyView mView) {
        this.mView = mView;
    }

    @Override
    public void getConstituencyData(int state_id, int mp_id, int mla_id) {
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
                mView.onConstituencyServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getMlaPastElection(state_id, mp_id, mla_id);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void getConstituencyList(int state_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onConstituencyList(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onConstituencyListApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onConstituencyServerError(serverException);

            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getConstituencyList(state_id);
        apiHandler.getData(apiResponseCall);
    }
}
