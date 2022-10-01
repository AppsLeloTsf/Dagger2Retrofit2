package com.molitics.molitician.httpapi;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiHandler {
    private static ApiHandler mInstance;
    private ApiResponseListener apiResponseListener;

    public void setApiResponseListener(ApiResponseListener apiResponseListener) {
        this.apiResponseListener = apiResponseListener;
    }

    private ApiHandler() {

    }

    public static ApiHandler getInstance() {

        return new ApiHandler();
    }

    public void getData(final Call<APIResponse> request) {
        request.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse> call, @NonNull Response<APIResponse> response) {
                int code = response.code();

                if (code == 200) {
                    int apiCode = response.body().getStatus();
                    String message = response.body().getMessage();
                    if (apiCode == 2000) {
                        apiResponseListener.onApiResponse(response.body());
                    } else {
                        ApiException serverException = new ApiException(apiCode, message);
                        apiResponseListener.onApiError(serverException);
                    }
                } else if (code == 300) {
                    int apiCode = response.body().getStatus();
                    String message = response.body().getMessage();
                    if (apiCode >= 1100 && apiCode < 1200) {
                        apiResponseListener.onFormValidationError(response.body().getError());
                    } else {
                        ApiException serverException = new ApiException(apiCode, message);
                        apiResponseListener.onApiError(serverException);
                    }
                } else if (code == 401) {

                    int apiCode = response.body().getStatus();
                    String message = response.body().getMessage();
                    if (apiCode >= 1100 && apiCode < 1200) {
                        apiResponseListener.onFormValidationError(response.body().getError());
                    } else {
                        ApiException serverException = new ApiException(apiCode, message);
                        apiResponseListener.onApiError(serverException);
                    }
                    // callback.unauthenticated(response);
                } else if (code >= 400 && code < 500) {
                    try {
                        String bodyString = new String(response.errorBody().bytes());
                        Gson gson = new Gson();
                        APIResponse apiResponse = gson.fromJson(bodyString, APIResponse.class);
                        ApiException serverException = new ApiException(apiResponse.getStatus(), apiResponse.getMessage());
                        Log.i("error_Message", apiResponse.getMessage());
                        apiResponseListener.onApiError(serverException);
                        Log.i("Message", bodyString);

                    } catch (Exception e) {
                        ApiException serverException = new ApiException(40000, "Something went wrong");
                        apiResponseListener.onApiError(serverException);
                    }

                    //callback.clientError(response);
                } else if (code >= 500 && code < 600) {
                    ApiException serverException = new ApiException(40000, "Something went wrong");
                    apiResponseListener.onApiError(serverException);

                } else {
                    ApiException serverException = new ApiException(40000, "Something went wrong");
                    apiResponseListener.onApiError(serverException);
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse> call, @NonNull Throwable t) {
                String message = t.getMessage();
                if (t instanceof ConnectException || t instanceof UnknownHostException || t instanceof SocketTimeoutException ) {
                    ApiException serverException = new ApiException(100003, message);
                    apiResponseListener.onApiError(serverException);
                } else {
                    ApiException serverException = new ApiException(100000, message);
                    apiResponseListener.onApiError(serverException);
                }
            }
        });
    }
//    public void getFeedData(final Call<FeedResponse> request) {
//        request.enqueue(new Callback<FeedResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<FeedResponse> call, @NonNull Response<FeedResponse> response) {
//                int code = response.code();
//
//                if (code == 200) {
//                    int apiCode = response.body().getStatus();
////                    String message = response.body().getMessage();
//                    String message = "error";
//
//                    if (apiCode == 2000) {
//                        apiResponseListener.onApiResponse(response.body());
//                    } else {
//                        ApiException serverException = new ApiException(apiCode, message);
//                        apiResponseListener.onApiError(serverException);
//                    }
//                } else if (code == 300) {
//                    int apiCode = response.body().getStatus();
////                    String message = response.body().getMessage();
//                    String message = "error";
//
//                    if (apiCode >= 1100 && apiCode < 1200) {
////                        apiResponseListener.onFormValidationError(response.body().getError());
//                    } else {
//                        ApiException serverException = new ApiException(apiCode, message);
//                        apiResponseListener.onApiError(serverException);
//                    }
//                } else if (code == 401) {
//
//                    int apiCode = response.body().getStatus();
////                    String message = response.body().getMessage();
//                    String message = "error";
//                    if (apiCode >= 1100 && apiCode < 1200) {
////                        apiResponseListener.onFormValidationError(response.body().getError());
//                    } else {
//                        ApiException serverException = new ApiException(apiCode, message);
//                        apiResponseListener.onApiError(serverException);
//                    }
//                    // callback.unauthenticated(response);
//                } else if (code >= 400 && code < 500) {
//                    try {
//                        String bodyString = new String(response.errorBody().bytes());
//                        Gson gson = new Gson();
//                        APIResponse apiResponse = gson.fromJson(bodyString, APIResponse.class);
//                        ApiException serverException = new ApiException(apiResponse.getStatus(), apiResponse.getMessage());
//                        Log.i("error_Message", apiResponse.getMessage());
//                        apiResponseListener.onApiError(serverException);
//                        Log.i("Message", bodyString);
//
//                    } catch (Exception e) {
//                        ApiException serverException = new ApiException(40000, "Something went wrong");
//                        apiResponseListener.onApiError(serverException);
//                    }
//
//                    //callback.clientError(response);
//                } else if (code >= 500 && code < 600) {
//                    ApiException serverException = new ApiException(40000, "Something went wrong");
//                    apiResponseListener.onApiError(serverException);
//
//                } else {
//                    ApiException serverException = new ApiException(40000, "Something went wrong");
//                    apiResponseListener.onApiError(serverException);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<FeedResponse> call, @NonNull Throwable t) {
//                String message = t.getMessage();
//                if (t instanceof ConnectException || t instanceof UnknownHostException || t instanceof SocketTimeoutException ) {
//                    ApiException serverException = new ApiException(100003, message);
//                    apiResponseListener.onApiError(serverException);
//                } else {
//                    ApiException serverException = new ApiException(100000, message);
//                    apiResponseListener.onApiError(serverException);
//                }
//            }
//        });
//    }

}
