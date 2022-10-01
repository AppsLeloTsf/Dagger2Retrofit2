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

public class SurveyDetailHandler implements SurveyDetailPresenter.UserRequest {

    SurveyDetailPresenter.SurveyDetailsViews mView;

    SurveyDetailHandler(SurveyDetailPresenter.SurveyDetailsViews mView) {
        this.mView = mView;
    }

    @Override
    public void hitSurveyDetailRequest( int survey_id) {

        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onSurveyDetailResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {

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
}
