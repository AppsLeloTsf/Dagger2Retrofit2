package com.molitics.molitician.ui.dashboard.splash;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 4/17/2017.
 */

public class SplashHandler implements SplashPresenter.SplashRequest {

    SplashPresenter.SplashView mView;

    public SplashHandler(SplashPresenter.SplashView mView) {
        this.mView = mView;
    }

    @Override
    public void getVersion(int version) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onVersionResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onVersionException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().checkUpdate(version);
        apiHandler.getData(apiResponseCall);
    }
}
