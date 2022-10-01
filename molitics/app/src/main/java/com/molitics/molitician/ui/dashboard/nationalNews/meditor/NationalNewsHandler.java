package com.molitics.molitician.ui.dashboard.nationalNews.meditor;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;


public class NationalNewsHandler implements NewsPresenter.UserActionsListener {

    private final NewsPresenter.NewsView mView;

    public NationalNewsHandler(NewsPresenter.NewsView mView) {
        this.mView = mView;
    }

    @Override
    public void getNews(int pageNo) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onNewsDone(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onNewsAPIError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
                mView.onFormValidationError(formValidation);
            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onNewsServerFailed(serverException);

            }

        });
        Call<APIResponse> request = RetrofitRestClient.getInstance().getNews(pageNo, 0);
        apiHandler.getData(request);

    }

    @Override
    public void getLocalNews(int value, int pageNo) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onNewsDone(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onNewsAPIError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onNewsServerFailed(serverException);
            }


        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getLocalNews(value, pageNo);
        apiHandler.getData(request);
    }

    @Override
    public void getVideoByLanguage(int language, int pageNo, int displayType, int is_not_live) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onVideoLanguageSelection(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onVideoLanguageError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onNewsServerFailed(serverException);
            }
        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getVideoByLanguage(language, pageNo, displayType, is_not_live);
        apiHandler.getData(request);
    }
}
