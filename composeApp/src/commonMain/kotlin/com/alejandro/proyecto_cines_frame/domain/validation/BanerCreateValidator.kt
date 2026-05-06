package com.alejandro.proyecto_cines_frame.domain.validation

import com.alejandro.proyecto_cines_frame.domain.model.input.BanerCreateInput

/**
 * Valida la creación/edidición de un banner
 */
object BanerCreateValidator {

    /**
     * Valida la creación de un banner
     */
    fun validateCreate(input: BanerCreateInput): FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        val peliculaErrors = mutableListOf<FieldError>()
        val peliculaId = input.peliculaId.trim()
        if (peliculaId.isBlank()) peliculaErrors.add(FieldError.Required("peliculaId"))
        if (peliculaErrors.isNotEmpty()) errors["peliculaId"] = peliculaErrors

        val urlErrors = mutableListOf<FieldError>()
        val url = input.url.trim()
        if (url.isBlank()) urlErrors.add(FieldError.Required("url"))
        else if (url.length > 511) urlErrors.add(FieldError.TooLong("url", 511))
        if (urlErrors.isNotEmpty()) errors["url"] = urlErrors

        val fechasErrors = mutableListOf<FieldError>()
        val empieza = input.empieza
        val termina = input.termina
        if(empieza >= termina) fechasErrors.add(FieldError.Custom("La fecha de inicio debe ser anterior a la de fin."))
        if (fechasErrors.isNotEmpty()) errors["fechas"] = fechasErrors

        return errors
    }
}