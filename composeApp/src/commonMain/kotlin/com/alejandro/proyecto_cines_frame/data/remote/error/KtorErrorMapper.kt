package com.alejandro.proyecto_cines_frame.data.remote.error

import com.alejandro.proyecto_cines_frame.core.error.AppError
import io.ktor.client.plugins.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

suspend fun Throwable.toAppError(): AppError {
    return when (this) {
        is RedirectResponseException, // 3xx
        is ClientRequestException,    // 4xx
        is ServerResponseException -> { // 5xx
            val status = (this as ResponseException).response.status
            val text = (this as ResponseException).response.bodyAsText()

            when (status) {
                HttpStatusCode.Unauthorized -> AppError.Unauthorized(text)
                HttpStatusCode.Forbidden -> AppError.Forbidden(text)
                HttpStatusCode.NotFound -> AppError.NotFound(text)
                HttpStatusCode.BadRequest -> {
                    val fields = runCatching {
                        Json.parseToJsonElement(text).jsonObject
                            .mapValues { it.value.jsonPrimitive.content }
                    }.getOrDefault(emptyMap())
                    AppError.Validation(fields)
                }
                else -> AppError.Server(status.value, text)
            }
        }

        is java.io.IOException -> AppError.Network(message)
        else -> AppError.Unknown(message)
    }
}