package com.alejandro.proyecto_cines_frame.domain.validation

import com.alejandro.proyecto_cines_frame.domain.model.input.AlergenoCreateInput

/**
 * Valida la creación/edidición de alérgenos
 */
object AlergenoValidator {

    /**
     * Valida la creación/edición de alérgenos
     */
    fun validateCreate(input: AlergenoCreateInput): FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        // nombre
        val nombreErrors = mutableListOf<FieldError>()
        val nombre = input.nombre.trim()
        if (nombre.isBlank()) nombreErrors.add(FieldError.Required("nombre"))
        else if (nombre.length > 25) nombreErrors.add(FieldError.TooLong("nombre", 25))
        if (nombreErrors.isNotEmpty()) errors["nombre"] = nombreErrors

        if (input.antiguoNombre != null && nombre == input.antiguoNombre.trim())
            errors["igualdad"] = listOf(FieldError.Custom("El antiguo nombre y el nuevo no pueden ser iguales."))

        return errors
    }

}