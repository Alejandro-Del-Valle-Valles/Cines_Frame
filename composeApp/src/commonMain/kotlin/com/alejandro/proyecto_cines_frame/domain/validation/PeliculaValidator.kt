package com.alejandro.proyecto_cines_frame.domain.validation

import com.alejandro.proyecto_cines_frame.domain.model.input.PeliculaCreateInput

/**
 * Validador de creación/edición de películas
 */
object PeliculaValidator {

    /**
     * Valida los datos de la película, y los que sean erróneos, genera un mensaje
     */
    fun validateCreate(input: PeliculaCreateInput): ValidationResult {
        val errors = mutableListOf<ValidationError>()

        // nombre
        val nombre = input.nombre.trim()
        if (nombre.isBlank())
            errors += ValidationError("nombre", "El nombre no puede estar vacío.")
        else if (nombre.length > 50)
            errors += ValidationError("nombre", "El nombre no puede tener más de 50 caracteres.")

        // descripcion
        val descripcion = input.descripcion?.trim()
        if (descripcion != null && descripcion.length > 511)
            errors += ValidationError("descripcion", "La descripción no puede tener más de 511 caracteres.")


        // genero
        if (input.genero == null)
            errors += ValidationError("genero", "Debes seleccionar un género.")

        // url (opcional)
        val url = input.url?.trim()
        if (url != null && url.length > 511)
            errors += ValidationError("url", "La URL no puede tener más de 511 caracteres.")

        // (opcional) validación simple de formato
        if (url != null && url.isNotEmpty() && !url.startsWith("http://") && !url.startsWith("https://"))
            errors += ValidationError("url", "La URL debe empezar por http:// o https://")

        // duracion: HH:mm
        if (!isValidHourMinute(input.duracion))
            errors += ValidationError("duracion", "La duración debe tener formato HH:mm (por ejemplo 01:34).")

        // edad
        val edad = input.edad
        if (edad != null && edad < 0)
            errors += ValidationError("edad", "La edad debe ser 0 o mayor.")

        // participantes (si quieres exigirlos)
        input.participantes.forEachIndexed { index, p ->
            if (p.id == null || p.id <= 0)
                errors += ValidationError("participantes[$index].id", "El ID del participante debe ser positivo.")
            if (p.roles.isEmpty())
                errors += ValidationError("participantes[$index].roles", "El participante debe tener al menos un rol.")
        }

        return ValidationResult(errors)
    }

    /**
     * Valida que el valor introducido tenga un formato númeríco separado por ':' y esté comprendido entre
     * 00-23 en las horas y 00:59 en los minutos
     */
    private fun isValidHourMinute(value: String): Boolean {
        val regex = Regex("^([01]\\d|2[0-3]):([0-5]\\d)$")
        return regex.matches(value.trim())
    }
}