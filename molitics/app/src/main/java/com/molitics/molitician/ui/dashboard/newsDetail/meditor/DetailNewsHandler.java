package com.molitics.molitician.ui.dashboard.newsDetail.meditor;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 12/23/2016.
 */

public class DetailNewsHandler implements DetailNewsPresenter.userRequest {
    DetailNewsPresenter.NewsDetailView mmView;

    public DetailNewsHandler(DetailNewsPresenter.NewsDetailView mView) {
        this.mmView = mView;
    }

    @Override
    public void getNewsDetail(int news_id,int display_type) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mmView.onNewsDetailDone(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mmView.onAPIError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
                mmView.onFormValidationError(formValidation);
            }

            @Override
            public void onServerError(ServerException serverException) {
                mmView.onNewsDetailServerFailed(serverException);
            }
        });
        Call<APIResponse> request = RetrofitRestClient.getInstance().getNewsDetail(news_id,display_type );
        apiHandler.getData(request);
    }

    @Override
    public void getListDetail(int news_id,int display_type) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mmView.onListDetailDone(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mmView.onAPIError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
                mmView.onFormValidationError(formValidation);
            }

            @Override
            public void onServerError(ServerException serverException) {
                mmView.onNewsDetailServerFailed(serverException);
            }
        });
        Call<APIResponse> request = RetrofitRestClient.getInstance().getNewsDetail(news_id,display_type);
        apiHandler.getData(request);
    }
}
