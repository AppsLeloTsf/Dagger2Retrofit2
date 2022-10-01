package com.molitics.molitician.ui.dashboard.articles.feedArticle;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

import okhttp3.MultipartBody;

/**
 * Created by om on 07/04/18.
 */

public class AddArticlePresenter {


    public interface AddArticleView {

        void onAddArticleResponse(APIResponse apiResponse);

        void onAddArticleError(ApiException apiException);

        void onEditArticleResponse(APIResponse apiResponse);

        void onEditArticleError(ApiException apiException);
    }

    public interface AddArticleRequest {
        void addArticle(MultipartBody.Part image, String heading, String content);

        void editArticle(int id, MultipartBody.Part image, String heading, String content);

        void editArticleNonImage(int id, String heading, String content);
    }

}
