package com.alejandro.proyecto_cines_frame.core.error

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val error: AppError) : ApiResult<Nothing>()
}