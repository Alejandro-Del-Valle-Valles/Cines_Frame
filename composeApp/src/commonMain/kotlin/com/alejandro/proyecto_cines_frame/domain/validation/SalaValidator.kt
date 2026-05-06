package com.alejandro.proyecto_cines_frame.domain.validation

import com.alejandro.proyecto_cines_frame.domain.model.input.SalaCreateInput

/**
 * Valida la creación/edición de salas
 */
object SalaValidator {

    /**
     * Valida la creación/edición de salas
     */
    fun validateCreate(input: SalaCreateInput): FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        if(input.numero <= 0) errors["numero"] = listOf(FieldError.Required("numero"))
        if(input.aforo <= 0) errors["aforo"] = listOf(FieldError.Required("aforo"))

        return errors
    }
}