package com.molitics.molitician.ui.dashboard.election.past_election;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 1/9/2017.
 */

public class PastElectionHandler implements PastElectionPresenter.PastUserRequest {
    PastElectionPresenter.PastElectionView mView;


    public PastElectionHandler(PastElectionPresenter.PastElectionView mView) {
        this.mView = mView;
    }

    @Override
    public void getPastElection(int page_no,String name) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onPastElectionResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onPastElectionApiException(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onPastElectionServerError(serverException);
            }

        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getPastElection(page_no,name);
        apiHandler.getData(request);

    }
}
