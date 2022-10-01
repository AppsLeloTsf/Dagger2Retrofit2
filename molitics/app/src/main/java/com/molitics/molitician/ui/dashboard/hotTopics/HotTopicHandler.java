package com.molitics.molitician.ui.dashboard.hotTopics;


import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 27/11/17.
 */

public class HotTopicHandler implements HotTopicPresenter.HotTopicRequest {

    HotTopicPresenter.HotTopicUI mView;

    public HotTopicHandler(HotTopicPresenter.HotTopicUI mView) {
        this.mView = mView;
    }

    @Override
    public void getHotTopic(int count) {
        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onHotTopicResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onHotTopicError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });
        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getHotTopic(count);
        apiHandler.getData(apiResponseCall);
    }
}
