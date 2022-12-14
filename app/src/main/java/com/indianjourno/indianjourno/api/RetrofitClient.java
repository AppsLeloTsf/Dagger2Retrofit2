package com.indianjourno.indianjourno.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    private RetrofitClient(){
        String baseUrl = "https://www.janatasuddi.com/api/";
        String baseUrlIndian = "https://indianjourno.com/";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrlIndian)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){

        if(mInstance==null){
            mInstance = new RetrofitClient();
        }
        return  mInstance;
    }

    public Api getApi(){

        return retrofit.create(Api.class);
    }

}
