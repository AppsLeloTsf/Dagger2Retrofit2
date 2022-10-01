package com.molitics.molitician.ui.dashboard.survey;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by Rahul on 10/18/2016.
 */

public class SurveyHandler implements SurveyPresenter.UserRequest {
    SurveyPresenter.SurveyView mView;

    SurveyHandler(SurveyPresenter.SurveyView mView) {
        this.mView = mView;
    }
    @Override
    public void hitSurveyRequest(int page_no, Integer state_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onSurveyResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onSurveyApiException(apiException);

            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {


            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onSurveyServerException(serverException);
            }

        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().surveyList(page_no, state_id);

        apiHandler.getData(request);

    }
}
