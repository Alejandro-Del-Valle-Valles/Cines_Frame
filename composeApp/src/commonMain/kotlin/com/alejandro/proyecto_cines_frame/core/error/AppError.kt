// core/error/AppError.kt
package com.alejandro.proyecto_cines_frame.core.error

sealed class AppError {
    data class Validation(val fields: Map<String, String>) : AppError()  // 400
    data class Unauthorized(val details: Map<String, String>) : AppError() // 401
    data class Forbidden(val details: Map<String, String>) : AppError()    // 403
    data class NotFound(val details: Map<String, String>) : AppError()     // 404
    data class Conflict(val details: Map<String, String>) : AppError()     // 409
    data class Server(val code: Int, val details: Map<String, String>) : AppError()
    data class Network(val message: String? = null) : AppError()
    data class Unknown(val message: String? = null) : AppError()
}