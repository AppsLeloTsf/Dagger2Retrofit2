package com.molitics.molitician.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.molitics.molitician.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestBaseAdapter {

    public static Retrofit getConnectionAdapter(int state) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(AppRequestInterceptor.getRequestInterceptor(state))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return restAdapter;
    }
}
