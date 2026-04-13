package com.alejandro.proyecto_cines_frame.core.error


sealed class AppError {
    data class Network(val message: String? = null) : AppError()
    data class Unauthorized(val message: String? = null) : AppError() // 401
    data class Forbidden(val message: String? = null) : AppError()    // 403
    data class NotFound(val message: String? = null) : AppError()     // 404
    data class Validation(val fields: Map<String, String>) : AppError() // 400 con mapa
    data class Server(val code: Int, val message: String? = null) : AppError() // 5xx u otros
    data class Unknown(val message: String? = null) : AppError()
}