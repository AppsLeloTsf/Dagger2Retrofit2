package com.molitics.molitician.ui.dashboard.voice;


import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.voice.model.ImageDeleteModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceCreateModel;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * Created by rahul on 16/11/17.
 */

public class VoiceAddHandler implements VoiceAddPresenter.VoiceRequest, VoiceAddPresenter.VoiceVideo {

    VoiceAddPresenter.VoiceUI mView;

    VoiceAddPresenter.VoiceVideoResponse video_view;

    public VoiceAddHandler(VoiceAddPresenter.VoiceUI mView) {
        this.mView = mView;
    }

    public VoiceAddHandler(VoiceAddPresenter.VoiceVideoResponse video_view) {

        this.video_view = video_view;
    }

    @Override
    public void searchCandidateByName(String name) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onSearchResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onSearchApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getSearchCandidatesByName(name);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void createVoice(VoiceCreateModel voiceCreateModel) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                /// mView.onCreateVoiceResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                ///mView.onCreateVoiceApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().createVoice(voiceCreateModel);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void editVoice(int issue_id, VoiceCreateModel voiceAllModel) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
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
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().editVoice(issue_id, voiceAllModel);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void deleteVoiceImage(int issue_id, ImageDeleteModel image_location) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onDeleteResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onDeleteError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().deleteIssueImage(issue_id, image_location);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void postVideo(int voice_id, MultipartBody.Part[] body) {

        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                video_view.onVideoResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                video_view.onVideoApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
                video_view.onVideoServerError(serverException);
            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().createdVoiceImageUpload(voice_id, body);
        apiHandler.getData(apiResponseCall);
    }
}
