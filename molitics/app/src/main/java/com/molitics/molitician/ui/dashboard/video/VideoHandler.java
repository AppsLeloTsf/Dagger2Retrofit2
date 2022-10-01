package com.molitics.molitician.ui.dashboard.video;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 07/11/17.
 */

public class VideoHandler implements VideoPresenter.VideoRequest {

    VideoPresenter.VideoResponse mView;

    public VideoHandler(VideoPresenter.VideoResponse mView) {
        this.mView = mView;
    }

    @Override
    public void getAllVideo(int page_no) {

        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onVideoUi(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onVideoApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
            }

            @Override
            public void onServerError(ServerException serverException) {
            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getAllVideo(page_no);
        apiHandler.getData(apiResponseCall);
    }

    @Override
    public void getVideoByLanguage(int language, int pageNo, int display_type, int is_not_live) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onVideoLanguageSelection(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onVideoApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
            }


        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().getVideoByLanguage(language, pageNo, display_type, is_not_live);
        apiHandler.getData(request);
    }

}
