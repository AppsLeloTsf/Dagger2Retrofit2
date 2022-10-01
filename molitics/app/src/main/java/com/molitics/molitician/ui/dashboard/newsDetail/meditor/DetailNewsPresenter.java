package com.molitics.molitician.ui.dashboard.newsDetail.meditor;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

/**
 * Created by rahul on 12/23/2016.
 */

public class DetailNewsPresenter {

    public interface NewsDetailView {
        void onNewsDetailDone(APIResponse apiResponse);

        void onListDetailDone(APIResponse apiResponse);

        void onNewsDetailServerFailed(ServerException serverException);

        void onAPIError(ApiException apiException);

        void onFormValidationError(Map<String, String> errors);
    }

    public interface userRequest {
        void getNewsDetail(int news_id,int display_type);
        void getListDetail(int news_id,int display_type);
    }
}
