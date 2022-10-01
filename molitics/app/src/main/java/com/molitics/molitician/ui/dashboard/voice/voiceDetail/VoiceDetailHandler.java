package com.molitics.molitician.ui.dashboard.voice.voiceDetail;


import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 15/01/18.
 */

public class VoiceDetailHandler implements VoiceDetailPresenter.VoiceDetailRequest {
    VoiceDetailPresenter.VoiceDetailUi mView;

    public VoiceDetailHandler(VoiceDetailPresenter.VoiceDetailUi mView) {
        this.mView = mView;
    }

    @Override
    public void getDetailVoice(int voice_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onDetailVoiceResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onDetailVoiceException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getVoiceDetails(voice_id);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void deleteIssue(int issue_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onDeleteIssueResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onDeleteIssueError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().deleteIssue(issue_id);
        apiHandler.getData(responseCall);
    }
}
