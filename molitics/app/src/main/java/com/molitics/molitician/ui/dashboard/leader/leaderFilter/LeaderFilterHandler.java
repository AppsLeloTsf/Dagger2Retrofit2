package com.molitics.molitician.ui.dashboard.leader.leaderFilter;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 1/3/2017.
 */

public class LeaderFilterHandler implements LeaderFilterPresenter.UserRequest {
    LeaderFilterPresenter.LeaderFilterView mView;

    public LeaderFilterHandler(LeaderFilterPresenter.LeaderFilterView mView) {
        this.mView = mView;
    }

    @Override
    public void getStateList() {
        final ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onLeaderFilterStateList(apiResponse.getData().getState_list());
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLeaderFilterStateException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
                //    mView.onFormError(formValidation);
            }

            @Override
            public void onServerError(ServerException serverException) {
            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getStateList();
        apiHandler.getData(request);
    }

    @Override
    public void getOtherLocationList(int state_id) {

        final ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onDistrictUpdate(apiResponse.getData().getFilter_district_list());
                mView.onMlaUpdate(apiResponse.getData().getFilter_mla_list());
                mView.onMpUpdate(apiResponse.getData().getFilter_dmp_list());
                mView.onPartyUpdate(apiResponse.getData().getFilter_party_list());
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onLeaderApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
            }

            @Override
            public void onServerError(ServerException serverException) {
            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getExtraLocationFilter(state_id);
        apiHandler.getData(request);

    }

    @Override
    public void getFilterPost() {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onFilterPost(apiResponse.getData().getFilter_post_list());

            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onFilterPostException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
            }

            @Override
            public void onServerError(ServerException serverException) {
            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getFilterPost();
        apiHandler.getData(request);

    }
}
