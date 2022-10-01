package com.molitics.molitician.network;

import android.content.Context;

import retrofit2.Retrofit;

public class RequestResponses {
    public static APIService getObservableServiceRequest(Context context, int state) {
        Retrofit retrofit = RestBaseAdapter.getConnectionAdapter(state);
        return retrofit.create(APIService.class);
    }

}
