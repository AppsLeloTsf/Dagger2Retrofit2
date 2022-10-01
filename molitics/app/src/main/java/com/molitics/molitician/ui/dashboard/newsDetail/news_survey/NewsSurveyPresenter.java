package com.molitics.molitician.ui.dashboard.newsDetail.news_survey;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 12/26/2016.
 */

public class NewsSurveyPresenter {

    public interface SurveyView {
        void onSubmitSurvey(APIResponse apiResponse);

        void onSubmitSurveyServerError(ServerException serverException);

        void onSubmitSurveyAPIError(ApiException apiException);
    }

    public interface Request {
        void submitSurvey(int id, int response);
    }
}
