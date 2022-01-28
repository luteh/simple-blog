package com.example.simpleblog.common

import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException

sealed class MyFailure(open val message: String) {
    data class RequestTimeout(override val message: String) : MyFailure(message)
    data class NoInternetConnection(override val message: String) : MyFailure(message)
    data class DefaultError(override val message: String) : MyFailure(message)
    data class UnexpectedError(override val message: String) : MyFailure(message)
}

fun Exception.parseToFailure(): MyFailure {
    return when (this) {
        is HttpException -> MyFailure.DefaultError(this.message())
        is SocketException, is NoConnectionException -> MyFailure.NoInternetConnection("No Internet Connection")
        is SocketTimeoutException -> MyFailure.RequestTimeout("Request Timeout")
        else -> MyFailure.UnexpectedError("Something went wrong")
    }
}

