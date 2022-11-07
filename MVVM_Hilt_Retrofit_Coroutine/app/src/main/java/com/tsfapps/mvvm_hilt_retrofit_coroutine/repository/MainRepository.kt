package com.tsfapps.mvvm_hilt_retrofit_coroutine.repository

import com.tsfapps.mvvm_hilt_retrofit_coroutine.models.Post
import com.tsfapps.mvvm_hilt_retrofit_coroutine.network.ApiServiceImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiServiceImp: ApiServiceImp){

    fun getPost(): Flow<List<Post>> = flow {
        emit(apiServiceImp.getPost())
    }.flowOn(Dispatchers.IO)
}