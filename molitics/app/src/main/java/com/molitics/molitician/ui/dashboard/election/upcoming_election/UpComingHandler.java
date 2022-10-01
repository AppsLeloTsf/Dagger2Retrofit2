package com.molitics.molitician.ui.dashboard.election.upcoming_election;

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

public class UpComingHandler implements UpComingPresenter.UpComingUserRequest {
    UpComingPresenter.UpComingView mView;

    public UpComingHandler(UpComingPresenter.UpComingView mView) {
        this.mView = mView;
    }

    @Override
    public void getUpComingElection(int page_no) {
        ApiHandler apiHandler =ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onUpComingResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onUpComingApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onUpComingServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getUpComingElection(page_no);
        apiHandler.getData(apiResponseCall
        );


    }
}
