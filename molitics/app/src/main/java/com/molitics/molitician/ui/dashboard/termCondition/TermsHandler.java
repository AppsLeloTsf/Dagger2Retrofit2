package com.molitics.molitician.ui.dashboard.termCondition;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 1/18/2017.
 */

public class TermsHandler implements TermPresenter.TermRequest {

    TermPresenter.TermPresenterView mView;

    public TermsHandler(TermPresenter.TermPresenterView mView) {
        this.mView = mView;
    }

    @Override
    public void getTerms() {

        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onTermsResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onTermsApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getTermCondition();
        apiHandler.getData(apiResponseCall);
    }
}
