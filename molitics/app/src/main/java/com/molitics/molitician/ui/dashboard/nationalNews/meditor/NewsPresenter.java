package com.molitics.molitician.ui.dashboard.nationalNews.meditor;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;


public class NewsPresenter {
    public interface NewsView {
        void onNewsDone(APIResponse loginResponse);

        void onNewsServerFailed(ServerException serverException);

        void onNewsAPIError(ApiException apiException);

        void onFormValidationError(Map<String, String> errors);

        default void onVideoLanguageSelection(APIResponse loginResponse) {
        }

        default void onVideoLanguageError(ApiException apiException) {
        }

    }

    public interface UserActionsListener {
        void getNews(int pageNo);

        void getLocalNews(int value, int pageNo);

        void getVideoByLanguage(int language, int pageNo, int display_type, int is_not_live);
    }
}
