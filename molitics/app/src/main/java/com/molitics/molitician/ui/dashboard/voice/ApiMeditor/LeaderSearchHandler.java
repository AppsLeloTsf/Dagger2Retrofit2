package com.molitics.molitician.ui.dashboard.voice.ApiMeditor;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.leader.LeaderPresenter;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul; on 25/05/18.
 */

public class LeaderSearchHandler implements LeaderPresenter.LeaderSearchRequest {

    LeaderPresenter.LeaderListResponse mView;

  public   LeaderSearchHandler(LeaderPresenter.LeaderListResponse mView) {
        this.mView = mView;
    }

    @Override
    public void searchCandidate(int page_no, String name) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onLeaderSearchSuccess(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLeaderSearchError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getSearchCandidatesByName(name);
        apiHandler.getData(apiResponseCall);
    }
}
