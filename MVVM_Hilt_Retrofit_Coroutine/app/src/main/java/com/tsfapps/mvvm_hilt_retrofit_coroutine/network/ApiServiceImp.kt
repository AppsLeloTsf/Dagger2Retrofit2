package com.tsfapps.mvvm_hilt_retrofit_coroutine.network

import com.tsfapps.mvvm_hilt_retrofit_coroutine.models.Post
import javax.inject.Inject

class ApiServiceImp @Inject constructor(private val apiService: ApiService) {

    suspend fun getPost(): List<Post> = apiService.getPost()
}