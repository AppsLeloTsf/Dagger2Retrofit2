package com.molitics.molitician.httpapi;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

/**
 * Created by rohitkumar on 9/30/16.
 */
public interface ApiResponseListener {

    void onApiResponse(APIResponse apiResponse);

//    void onApiResponse(FeedResponse feedResponse);

    void onApiError(ApiException apiException);

    void onFormValidationError(Map<String, String> formValidation);

    void onServerError(ServerException serverException);

    // public void networkFail(ApiException apiException);
}
