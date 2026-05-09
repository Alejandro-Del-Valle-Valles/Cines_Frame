package com.alejandro.proyecto_cines_frame.domain.validation

import com.alejandro.proyecto_cines_frame.domain.model.input.TipoEntradaCreateInput

/**
 * Valida la creación/edición de tipos de entrada
 */
object TipoEntradaValidator {

    /**
     * Valida la creación/edición de tipos de entrada
     */
    fun validateCreate(input: TipoEntradaCreateInput): FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        if(input.id != null && input.id <= 0)
                errors["id"] = listOf(FieldError.Custom("El ID debe ser positivo."))

        val nombreErrors = mutableListOf<FieldError>()
        val nombre = input.nombre.trim()
        if (nombre.isBlank()) nombreErrors.add(FieldError.Required("nombre"))
        else if (nombre.length > 20) nombreErrors.add(FieldError.TooLong("nombre", 20))

        val descripcionErrors = mutableListOf<FieldError>()
        val descripcion = input.descripcion.trim()
        if (descripcion.isBlank()) descripcionErrors.add(FieldError.Required("descripcion"))
        else if (descripcion.length > 50) descripcionErrors.add(FieldError.TooLong("descripcion", 50))

        val precioErrors = mutableListOf<FieldError>()
        val precio = input.precio
        if (precio <= 0) precioErrors.add(FieldError.Required("precio"))

        return errors
    }
}