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

public class PartyListHandler implements PartyListPresenter.PartyUserRequest {
    PartyListPresenter.PartListView mView;

    public PartyListHandler(PartyListPresenter.PartListView mView) {
        this.mView = mView;
    }

    @Override
    public void getPartyList(int election_id, int party_id, int page_no) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onPartyListResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onPartyListApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onPartyListServerException(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getPartyList(election_id, party_id, page_no);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void getUpcomingConstituency(int election_id, final int mla_id, int mp_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onUpComingConstituencyResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onUpComingConstituencyApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onPartyListServerException(serverException);
            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getUpComingConstituency(election_id, mla_id);
        apiHandler.getData(apiResponseCall);
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
                mView.onPartyListApiException(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

                mView.onPartyListServerException(serverException);
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
                mView.onPartyListApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onPartyListServerException(serverException);
            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().unFollowCandidate(candidate_id);
        apiHandler.getData(request);
    }

    @Override
    public void getLeaderFollowed(int page_no) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onFollowedResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onFollowedApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onPartyListServerException(serverException);
            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getFollowingLeader(page_no);
        apiHandler.getData(request);
    }
}
