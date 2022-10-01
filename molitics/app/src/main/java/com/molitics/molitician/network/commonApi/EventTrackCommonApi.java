package com.molitics.molitician.network.commonApi;

import android.content.Context;

import com.molitics.molitician.network.APIService;
import com.molitics.molitician.network.RequestResponses;
import com.molitics.molitician.util.Util;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EventTrackCommonApi {

    public static void updateEventOnServer(Context context, Integer eventId, Integer actionType, String userId, String error, CommonCallBack callBack) {
        APIService requestURLs = RequestResponses.getObservableServiceRequest(context, 0);
        Map<String, Object> params = new HashMap<>();
        params.put("action", actionType);
        params.put("device_id", Util.getDeviceIdRegisteredOnServer(context));
       params.put("user", userId);
       params.put("error", error);
        requestURLs.trackEvent( eventId + "",params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(retrofit2.Response<Object> response) {
                        if (response != null && response.body() != null) {

                            callBack.onComplete(null);
                        }
                    }
                });
    }

}