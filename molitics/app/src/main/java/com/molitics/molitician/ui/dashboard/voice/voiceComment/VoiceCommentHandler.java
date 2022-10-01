package com.molitics.molitician.ui.dashboard.voice.voiceComment;


import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 20/11/17.
 */

public class VoiceCommentHandler implements VoiceCommentPresenter.VoiceCommentRequest {


    VoiceCommentPresenter.VoiceCommentUI mView;

    public VoiceCommentHandler(VoiceCommentPresenter.VoiceCommentUI mView) {
        this.mView = mView;
    }


    @Override
    public void getAllComment(int issue_id, int count) {

        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onAllCommentResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onAllCommentApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getAllComment(issue_id, count);
        apiHandler.getData(apiResponseCall);

    }

    @Override
    public void postComment(int issue_id, String comment) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onPostCommentResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onPostCommentApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().issueComment(issue_id, comment);
        apiHandler.getData(apiResponseCall);

    }

    @Override
    public void editComment(int issue_id, int comment_id, String edit_comment) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onEditCommentResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onEditCommentApiError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().editComment(issue_id, comment_id, edit_comment);
        apiHandler.getData(apiResponseCall);

    }

    @Override
    public void deleteCommet(int issue_id, int comment_id) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onDeleteCommentResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onDeleteCommentError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().deleteComment(comment_id);
        apiHandler.getData(apiResponseCall);
    }
}
