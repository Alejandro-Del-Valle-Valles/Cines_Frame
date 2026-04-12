package com.alejandro.proyecto_cines_frame.domain.validation

data class ValidationError(
    val field: String,   // "nombre", "duracion", "url", ...
    val message: String
)
