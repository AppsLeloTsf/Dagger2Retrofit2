package com.molitics.molitician.rxJava;


import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public abstract class CallbackWrapper<T extends APIResponse> extends DisposableObserver<T> {
    //BaseRxView is just a reference of a View in MVP
    private WeakReference<BaseRxView> weakReference;

    public CallbackWrapper(BaseRxView view) {
        this.weakReference = new WeakReference<>(view);
    }

    protected abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        //You can return StatusCodes of different cases from your API and handle it here. I usually include these cases on BaseResponse and iherit it from every Response
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        BaseRxView view = weakReference.get();
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            view.onApiError(getErrorMessage(responseBody));
        } else if (e instanceof SocketTimeoutException) {
            view.onTimeout();
        } else if (e instanceof IOException) {
            view.onNetworkError();
        } else {
            view.onUnknownError(e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        BaseRxView view = weakReference.get();
        view.onRequestComplete();
    }

    private ApiException getErrorMessage(ResponseBody responseBody) {
        try {

            JSONObject jsonObject = new JSONObject(responseBody.string());
            int code = jsonObject.optInt("status");
            String message = jsonObject.optString("message");
            String auth_key = jsonObject.optString("auth_key");

            return new ApiException(code, message);
        } catch (Exception e) {
            return new ApiException(0, e.getMessage());

        }
    }
}
