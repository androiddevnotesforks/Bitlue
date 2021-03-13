package com.carlosmesquita.technicaltest.n26.bitlue.utils

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Loading(val loadingMessage: String? = null) : Result<Nothing>()

    data class Error(val throwable: Throwable) : Result<Nothing>()
}
