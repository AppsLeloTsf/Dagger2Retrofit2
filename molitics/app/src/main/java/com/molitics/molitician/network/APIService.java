package com.molitics.molitician.network;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Piyush on 14-01-2016.
 */
public interface APIService {
    String EVENT_TRACK = "static/user-app-progress/";

    @FormUrlEncoded
    @retrofit2.http.POST(EVENT_TRACK+"{eventId}")
    Observable<Response<Object>> trackEvent(@Path("eventId") String eventId, @FieldMap Map<String, Object> params);

}