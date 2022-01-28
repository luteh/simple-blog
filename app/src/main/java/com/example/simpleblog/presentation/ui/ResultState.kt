package com.example.simpleblog.presentation.ui

import com.example.simpleblog.common.MyFailure

sealed interface ResultState<T> {
    class Initial<T> : ResultState<T>
    class Loading<T> : ResultState<T>
    data class Failure<T>(val myFailure: MyFailure) : ResultState<T>
    data class Success<T>(val data: T) : ResultState<T>
}