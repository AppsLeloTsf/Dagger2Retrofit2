package com.molitics.molitician.ui.dashboard.survey;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

/**
 * Created by Rahul on 10/19/2016.
 */

public class SurveyDetailPresenter {

    public interface SurveyDetailsViews {

        void onSurveyDetailResponse(APIResponse apiResponse);
        void onSurveyDetailApiException(ApiException apiException);
        void onSurveyDetailServerException(ServerException apiException);
        void onSurveyDetailFormValidation(Map<String,String> errors);

    }

    public interface UserRequest{

        void hitSurveyDetailRequest( int survey_id);
    }
}
