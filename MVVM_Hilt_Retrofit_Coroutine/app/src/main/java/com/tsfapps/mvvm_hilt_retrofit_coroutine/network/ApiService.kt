package com.tsfapps.mvvm_hilt_retrofit_coroutine.network

import com.tsfapps.mvvm_hilt_retrofit_coroutine.models.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPost(): List<Post>
}