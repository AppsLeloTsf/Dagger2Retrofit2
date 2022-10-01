package com.molitics.molitician.ui.dashboard.articles.feedArticle;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.articles.ArticlePresenter;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by om on 07/04/18.
 */

public class UserArticleHandler implements ArticlePresenter.UserArticleRequest {
    ArticlePresenter.UserArticleView mView;

    public UserArticleHandler(ArticlePresenter.UserArticleView mView) {
        this.mView = mView;
    }

    @Override
    public void getUserArticle(int page_no) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onUserArticleResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onUserArticleApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onUserArticleServerError(serverException);
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getUserArticle(page_no);
        apiHandler.getData(apiResponseCall);
    }

}
