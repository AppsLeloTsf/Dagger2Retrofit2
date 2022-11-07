package com.tsfapps.mvvm_hilt_retrofit_coroutine.ui

import com.tsfapps.mvvm_hilt_retrofit_coroutine.models.Post

sealed class ApiState{
    object Loading: ApiState()
    class Failure(val msg: Throwable): ApiState()
    class Success(val data: List<Post>): ApiState()
    object Empty: ApiState()


}
