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
 * Created by Rahul on 10/19/2016.
 */

public class TakeSurveyHandler implements TakeSurveyPresenter.UserRequest {
    TakeSurveyPresenter.TakeSurveyView mView;

    public TakeSurveyHandler(TakeSurveyPresenter.TakeSurveyView mView) {
        this.mView = mView;

    }

    @Override
    public void getSurveyDetail(int survey_id) {

        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onSurveyDetail(apiResponse);
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

            }


        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().getSurveyDetail(survey_id);
        apiHandler.getData(responseCall);
    }

    @Override
    public void submitSurvey(int survey_id, int response) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onSubmitSurveyResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onSubmitSurveyApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                mView.onSubmitSurveyServerException(serverException);
            }


        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().submitSurvey(survey_id, response);
        apiHandler.getData(responseCall);
    }
}
