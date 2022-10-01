package com.molitics.molitician.network;


import com.molitics.molitician.constants.GlobalConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.facebook.FacebookSdk.getCacheDir;

public class AppRequestInterceptor {

    public static OkHttpClient getRequestInterceptor(int state) {
        int cacheSize = 100 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getCacheDir(), cacheSize);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (state == GlobalConstants.NORMAL_REQUEST) {
            okHttpClient.addInterceptor(getLoggingInterceptor());
        }
        okHttpClient.cache(cache);
        okHttpClient.readTimeout(2, TimeUnit.MINUTES);
        okHttpClient.connectTimeout(2, TimeUnit.MINUTES);
        return okHttpClient.build();
    }

    private static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }
}

