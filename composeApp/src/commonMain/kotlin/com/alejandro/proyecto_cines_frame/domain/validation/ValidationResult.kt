package com.alejandro.proyecto_cines_frame.domain.validation

data class ValidationResult(
    val errors: List<ValidationError>
) {
    val isValid: Boolean get() = errors.isEmpty()

    fun messageFor(field: String): String? =
        errors.firstOrNull { it.field == field }?.message
}