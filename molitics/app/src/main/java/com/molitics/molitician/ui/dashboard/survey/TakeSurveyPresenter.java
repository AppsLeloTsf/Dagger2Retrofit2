package com.molitics.molitician.ui.dashboard.survey;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

/**
 * Created by Rahul on 10/19/2016.
 */

public class TakeSurveyPresenter {

    public interface TakeSurveyView {

        void onSurveyDetail(APIResponse apiResponse);

        void onSurveyApiException(ApiException apiException);

        void onSubmitSurveyResponse(APIResponse apiResponse);

        void onSubmitSurveyApiException(ApiException apiException);

        void onSubmitSurveyFormValidation(Map<String, String> errors);

        void onSubmitSurveyServerException(ServerException serverException);
    }

    public interface UserRequest {

        void getSurveyDetail(int survey_id);

        void submitSurvey(int survey_id,int response);

    }
}
