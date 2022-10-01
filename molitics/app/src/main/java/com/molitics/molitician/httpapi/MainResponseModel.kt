package com.molitics.molitician.httpapi

import com.molitics.molitician.model.Data

data class MainResponseModel(val result: String,
                             val message: String,
                             override val data: Data?,
                             override val exception: Throwable
) : ResponseResult, ErrorResult