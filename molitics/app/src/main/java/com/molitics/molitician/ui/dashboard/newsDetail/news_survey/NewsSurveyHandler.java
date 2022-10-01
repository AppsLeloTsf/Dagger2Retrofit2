package com.molitics.molitician.ui.dashboard.newsDetail.news_survey;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 12/26/2016.
 */

public class NewsSurveyHandler implements NewsSurveyPresenter.Request {
    NewsSurveyPresenter.SurveyView mSurveyView;

    public NewsSurveyHandler(NewsSurveyPresenter.SurveyView mSurveyView) {
        this.mSurveyView = mSurveyView;
    }

    @Override
    public void submitSurvey(int id, int response) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mSurveyView.onSubmitSurvey(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mSurveyView.onSubmitSurveyAPIError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mSurveyView.onSubmitSurveyServerError(serverException);
            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().submitSurvey(id, response);
        apiHandler.getData(responseCall);
    }
}
