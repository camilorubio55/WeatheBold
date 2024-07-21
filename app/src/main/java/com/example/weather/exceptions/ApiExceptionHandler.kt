package com.example.weather.exceptions

import com.example.weather.exceptions.ApiRequestException.AccessDenied
import com.example.weather.exceptions.ApiRequestException.ApiError
import com.example.weather.exceptions.ApiRequestException.Authentication
import com.example.weather.exceptions.ApiRequestException.ConnectionNetwork
import com.example.weather.exceptions.ApiRequestException.NotFound
import com.example.weather.exceptions.ApiRequestException.UnavailableServer
import com.example.weather.exceptions.ApiRequestException.Unknown
import java.net.UnknownHostException
import retrofit2.HttpException

class ApiExceptionHandler {

    fun cause(throwable: Throwable) = when (throwable) {
        is HttpException -> httpException(throwable)
        is UnknownHostException -> ConnectionNetwork()
        else -> Unknown()
    }

    private fun httpException(httpException: HttpException): ApiRequestException {
        val message = httpException.message()
        val code = httpException.code()

        return when {
            code == AUTHENTICATION_CODE -> Authentication()
            code == ACCESS_DENIED_CODE -> AccessDenied()
            code == RECEIPT_ERROR_NOT_FOUND_CODE -> NotFound()
            code >= UNAVAILABLE_SERVER_CODE -> UnavailableServer()
            message.isNotEmpty() -> ApiError(message)
            else -> Unknown()
        }
    }

    companion object {
        const val AUTHENTICATION_CODE = 401
        const val ACCESS_DENIED_CODE = 403
        const val RECEIPT_ERROR_NOT_FOUND_CODE = 404
        const val UNAVAILABLE_SERVER_CODE = 500
    }
}
