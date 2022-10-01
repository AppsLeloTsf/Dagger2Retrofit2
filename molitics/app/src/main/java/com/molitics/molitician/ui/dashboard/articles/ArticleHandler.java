package com.molitics.molitician.ui.dashboard.articles;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by Rahul on 3/15/2017.
 */

public class ArticleHandler implements ArticlePresenter.NewsRequest {
    ArticlePresenter.ArticleApi mView;

    public ArticleHandler(ArticlePresenter.ArticleApi mView) {
        this.mView = mView;
    }

    @Override
    public void getArticle(int page_no) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onArticleResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onArticleApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onArticleServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getArticle(page_no);
        apiHandler.getData(apiResponseCall);
    }

}
