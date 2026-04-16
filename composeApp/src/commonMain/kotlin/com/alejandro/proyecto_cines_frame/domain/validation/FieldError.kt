package com.alejandro.proyecto_cines_frame.domain.validation

sealed class FieldError {
    data class Required(val fieldName: String) : FieldError()
    data class InvalidFormat(val fieldName: String, val expectedFormat: String) : FieldError()
    data class TooShort(val fieldName: String, val minLength: Int) : FieldError()
    data class TooLong(val fieldName: String, val maxLength: Int) : FieldError()
    data class Custom(val message: String) : FieldError()
}

typealias FieldErrors = Map<String, List<FieldError>>

