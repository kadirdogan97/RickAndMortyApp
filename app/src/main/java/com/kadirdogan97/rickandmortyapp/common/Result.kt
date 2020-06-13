package com.kadirdogan97.rickandmortyapp.common


// references :
// https://developer.android.com/jetpack/docs/guide#addendum


sealed class Result<out T> {
    class Success<T>(val data: T) : Result<T>()
    class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> Result.Error(exception)
        is Result.Loading -> Result.Loading
    }
}

sealed class Status {

    object Content : Status()

    data class Error(val exception: Throwable) : Status()

    object Loading : Status()
}