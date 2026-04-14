package com.alejandro.proyecto_cines_frame.data.remote.error

import com.alejandro.proyecto_cines_frame.core.error.AppError
import io.ktor.client.plugins.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException

suspend fun Throwable.toAppError(): AppError {
    return when (this) {
        is ClientRequestException -> { // 4xx
            val status = response.status
            val raw = response.bodyAsText()
            val map = ApiErrorParser.parseToMap(raw)

            when (status) {
                HttpStatusCode.BadRequest -> AppError.Validation(map)
                HttpStatusCode.Unauthorized -> AppError.Unauthorized(map)
                HttpStatusCode.Forbidden -> AppError.Forbidden(map)
                HttpStatusCode.NotFound -> AppError.NotFound(map)
                HttpStatusCode.Conflict -> AppError.Conflict(map)
                else -> AppError.Server(status.value, map)
            }
        }

        is ServerResponseException -> { // 5xx
            val status = response.status
            val raw = response.bodyAsText()
            val map = ApiErrorParser.parseToMap(raw)
            AppError.Server(status.value, map)
        }

        is IOException -> AppError.Network(message)
        else -> AppError.Unknown(message)
    }
}