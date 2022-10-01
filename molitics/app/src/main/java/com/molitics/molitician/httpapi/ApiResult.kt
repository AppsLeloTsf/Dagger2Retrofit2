package com.molitics.molitician.httpapi

import com.molitics.molitician.model.Data
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.HttpException

/**
 * Sealed class of HTTP result
 */
@Suppress("unused")
public sealed class ApiResult<out T : Any> {
    /**
     * Successful result of request without errors
     */
    public class Ok<out T : Any>(
        public val value: T,
        override val data: Data?
    ) : ApiResult<T>(), ResponseResult {
        override fun toString() = "ApiResult.Ok{value=$value, response=$data}"
    }

    /**
     * HTTP error
     */
    public class Error(
            override val exception: HttpException,
            override val response: ResponseBody
    ) : ApiResult<Nothing>(), ErrorResult, ResponseError {
        override fun toString() = "ApiResult.Error{exception=$exception}"
    }

    /**
     * Network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response
     */
    public class Exception(
        override val exception: Throwable
    ) : ApiResult<Nothing>(), ErrorResult {
        override fun toString() = "ApiResult.Exception{$exception}"
    }

}

/**
 * Interface for [ApiResult] classes with [okhttp3.Response]: [ApiResult.Ok] and [ApiResult.Error]
 */
public interface ResponseResult {
    val data: Data?
}

/**
 * Interface for [ApiResult] classes with [okhttp3.Response]: [ApiResult.Ok] and [ApiResult.Error]
 */
public interface ResponseError {
    val response: ResponseBody
}

/**
 * Interface for [ApiResult] classes that contains [Throwable]: [ApiResult.Error] and [ApiResult.Exception]
 */
public interface ErrorResult {
    val exception: Throwable
}

/**
 * Returns [ApiResult.Ok.value] or `null`
 */
public fun <T : Any> ApiResult<T>.getOrNull(): T? = (this as? ApiResult.Ok)?.value

/**
 * Returns [ApiResult.Ok.value] or [default]
 */
public fun <T : Any> Result<T>.getOrDefault(default: T): T = getOrNull() ?: default

/**
 * Returns [ApiResult.Ok.value] or throw [throwable] or [ErrorResult.exception]
 */
public fun <T : Any> ApiResult<T>.getOrThrow(throwable: Throwable? = null): T {
    return when (this) {
        is ApiResult.Ok -> value
        is ApiResult.Error -> throw throwable ?: exception
        is ApiResult.Exception -> throw throwable ?: exception
    }
}
