package com.example.weather.extensions

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

fun <T : Any> Result<T>.error(message: String? = null) = (this as? Result.Error)?.exception ?: Exception(message ?: "unknown error")

fun <T : Any> Result<T>.error(exception: Exception) = (this as? Result.Error)?.exception ?: exception

fun <T : Any> Result<T>.success() = (this as? Result.Success)?.data
