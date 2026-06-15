package com.alejandro.proyecto_cines_frame.domain.validation

import com.alejandro.proyecto_cines_frame.domain.model.input.CodigoDescuentoCreateInput

/**
 * Validador de creación/edición de códigos de descuento
 */
object CodigoDescuentoValidator {

    /**
     * Valida los datos del código de descuento y genera un mapa de errores.
     */
    fun validateCreate(input: CodigoDescuentoCreateInput): FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        // codigo
        val codigoErrors = mutableListOf<FieldError>()
        val codigo = input.codigo.trim()

        if (codigo.isBlank())
            codigoErrors.add(FieldError.Required("codigo"))
        else if (codigo.length > 25)
            codigoErrors.add(FieldError.TooLong("codigo", 25))

        if (codigoErrors.isNotEmpty())
            errors["codigo"] = codigoErrors

        // condicion
        if (input.condicion == null)
            errors["condicion"] = listOf(FieldError.Required("condicion"))

        // porcentaje
        val porcentajeErrors = mutableListOf<FieldError>()

        if (input.porcentajeDescuento < 1)
            porcentajeErrors.add(
                FieldError.Custom(
                    "El porcentaje debe ser mayor que 0."
                )
            )

        if (input.porcentajeDescuento > 100)
            porcentajeErrors.add(
                FieldError.Custom(
                    "El porcentaje no puede ser superior a 100."
                )
            )

        if (porcentajeErrors.isNotEmpty())
            errors["porcentaje"] = porcentajeErrors

        return errors
    }
}