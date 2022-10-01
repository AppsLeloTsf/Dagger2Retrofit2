package com.molitics.molitician.ui.dashboard.leader;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.DeviceRegistration;
import com.molitics.molitician.ui.dashboard.leader.model.LeadersIdModel;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by lenovo on 10/13/2016.
 */

public class LeaderHandler implements LeaderPresenter.UserRequest {

    LeaderPresenter.LeaderView mView;

    public LeaderHandler(LeaderPresenter.LeaderView mView) {
        this.mView = mView;
    }

   /* @Override
    public void hitLeaderRequest(int page_no) {

        ApiHandler apiHandler = new ApiHandler();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onLeaderResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLeaderApiException(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

                mView.onLeaderFormValidation(formValidation);
            }

            @Override
            public void onServerError(ServerException serverException) {

                mView.onLeaderServerError(serverException);
            }

        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().leadersList(page_no);
        apiHandler.getData(request);

    }*/

    @Override
    public void searchCandidate(int page_no, String name) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onLeaderResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLeaderApiException(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
                mView.onLeaderFormValidation(formValidation);
            }

            @Override
            public void onServerError(ServerException serverException) {

                mView.onLeaderServerError(serverException);
            }

        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getSearchCandidate(page_no, name);
        apiHandler.getData(request);

    }

    @Override
    public void submitLocation(DeviceRegistration locationModel) {

        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onLocationSelectionResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLocationSelectionApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onLeaderServerError(serverException);
            }
        });
        Call<APIResponse> request = RetrofitRestClient.getInstance().submitLocation(locationModel);
        apiHandler.getData(request);
    }


    @Override
    public void getFilterCandidates(LeadersIdModel leadersIdModel) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onLeaderResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLeaderApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
                mView.onLeaderFormValidation(formValidation);
            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onLeaderServerError(serverException);
            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getFilterCandidates(leadersIdModel);
        apiHandler.getData(request);

    }

    @Override
    public void getAssemblyList(int state_value) {
        final ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onAssemblyList(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onAssemblyListException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onLeaderServerError(serverException);
            }
        });
        Call<APIResponse> request = RetrofitRestClient.getInstance().getLocationFromList(state_value);
        apiHandler.getData(request);
    }


}
