package com.molitics.molitician.httpapi

import com.google.gson.GsonBuilder
import com.molitics.molitician.BuildConfig
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.PrefUtil
import com.molitics.molitician.util.Util
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitRestClient {
    private lateinit var apiService: ApiService

    @JvmField
    var retrofit: Retrofit? = null

    @JvmStatic
    val instance: ApiService
        get() {
            if (retrofit == null) {
                synchronized(RetrofitRestClient::class.java) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    val client = getOkHttpClient(logging)
                    retrofit = getRetrofit(client, BuildConfig.BASE_URL)
                    apiService = retrofit!!.create(ApiService::class.java)
                }
            }
            return apiService
        }

    private fun getRetrofit(client: OkHttpClient, base_url: String): Retrofit {
        GsonBuilder()
                .setLenient()
                .create()
        return Retrofit.Builder()
                .baseUrl(base_url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun getOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .addInterceptor { chain: Interceptor.Chain ->
                    val original = chain.request()
                    val request = getDefaultRequest(original)
                    val response = chain.proceed(request)
                    response
                }
                .addInterceptor(logging)
                .build()
    }

    private fun getDefaultRequest(original: Request): Request {
        Util.showLog("AuthKey", PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY))
        return original.newBuilder().apply {
            if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isNotEmpty())
                addHeader("AuthKey", PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY))
        }
                .addHeader("language", PrefUtil.getConstantString(Constant.PreferenceKey.LANGUAGE))
                .addHeader("Content-Type", "application/json")
                .build()
    }
}