package com.molitics.molitician.ui.dashboard.survey;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by Rahul on 10/18/2016.
 */

public class SurveyPresenter {

    public interface SurveyView{

        void onSurveyResponse(APIResponse apiResponse);
        void onSurveyApiException(ApiException apiException);
        void onSurveyServerException(ServerException serverException);
    }

    public interface UserRequest{

        void hitSurveyRequest(int page_no,Integer state_id);
    }

}
