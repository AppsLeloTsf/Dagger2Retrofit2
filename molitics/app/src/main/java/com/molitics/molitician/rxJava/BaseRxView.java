package com.molitics.molitician.rxJava;


import com.molitics.molitician.error.ApiException;

public interface BaseRxView {
    void showLoading(String message);

    void onApiError(ApiException error);

    void onUnknownError(String error);

    void onTimeout();

    void onRequestComplete();

    void onNetworkError();

    ///boolean isNetworkConnected();

}