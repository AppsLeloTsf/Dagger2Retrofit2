package com.tsfapps.rxjavaretrofitpractice

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val tokenStorage: TokenStorage): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = tokenStorage.retrieveToken()?.let {
            chain.request().newBuilder()
                .addHeader("cache-control", "no-cache")
                .addHeader("Authorization", "Bearer $it")
                .build()
        }
        return request?.let { chain.proceed(it) }!!
    }
}