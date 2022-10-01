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
 * Created by rahul on 09/04/18.
 */

public class ArticleDeleteHandler implements ArticlePresenter.UserDetailArticle {
    ArticlePresenter.UserDetailResponse mView;

    ArticleDeleteHandler(ArticlePresenter.UserDetailResponse mView) {
        this.mView = mView;
    }

    @Override
    public void deleteUserArticle(int id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onDeleteResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onDeleteError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().deleteUserArticle(id);
        apiHandler.getData(apiResponseCall);
    }
}
