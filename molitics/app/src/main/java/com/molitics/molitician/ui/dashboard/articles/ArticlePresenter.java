package com.molitics.molitician.ui.dashboard.articles;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 3/15/2017.
 */

public class ArticlePresenter {

    public interface ArticleApi {
        void onArticleResponse(APIResponse apiResponse);

        void onArticleApiException(ApiException apiException);

        void onArticleServerError(ServerException server_Exception);

    }

    public interface NewsRequest {
        void getArticle(int page_no);
    }


    public interface UserArticleView {
        void onUserArticleResponse(APIResponse apiResponse);

        void onUserArticleApiException(ApiException apiException);

        void onUserArticleServerError(ServerException server_Exception);

    }

    public interface UserDetailArticle {
        void deleteUserArticle(int id);
    }

    public interface UserDetailResponse {

        void onDeleteResponse(APIResponse apiResponse);

        void onDeleteError(ApiException apiException);
    }

    public interface UserArticleRequest {
        void getUserArticle(int page_no);
    }
}
