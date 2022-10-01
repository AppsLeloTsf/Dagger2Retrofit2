package com.molitics.molitician.ui.dashboard.articles.feedArticle;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * Created by rahul on 07/04/18.
 */

public class AddArticleHandler implements AddArticlePresenter.AddArticleRequest {

    AddArticlePresenter.AddArticleView mView;

    AddArticleHandler(AddArticlePresenter.AddArticleView mView) {
        this.mView = mView;
    }

    @Override
    public void addArticle(MultipartBody.Part image, String title, String description) {


        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onAddArticleResponse(apiResponse);

            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onAddArticleError(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().addArticle(image, title, description);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void editArticle(int id, MultipartBody.Part image, String heading, String content) {


        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onEditArticleResponse(apiResponse);

            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onEditArticleError(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().editArticle(image, id, heading, content);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void editArticleNonImage(int id, String heading, String content) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onEditArticleResponse(apiResponse);

            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onEditArticleError(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });


        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().editArticleNonImage(id, heading, content);
        apiHandler.getData(apiResponseCall);
    }
}
