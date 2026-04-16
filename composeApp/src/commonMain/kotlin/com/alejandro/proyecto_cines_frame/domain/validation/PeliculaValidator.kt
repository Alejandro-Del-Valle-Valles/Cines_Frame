package com.alejandro.proyecto_cines_frame.domain.validation

import com.alejandro.proyecto_cines_frame.domain.model.input.PeliculaCreateInput

/**
 * Validador de creación/edición de películas
 */
object PeliculaValidator {

    /**
     * Valida los datos de la película, y los que sean erróneos, genera un mapa de errores
     */
    fun validateCreate(input: PeliculaCreateInput): FieldErrors {
        val errors = mutableMapOf<String, List<FieldError>>()

        // nombre
        val nombreErrors = mutableListOf<FieldError>()
        val nombre = input.nombre.trim()
        if (nombre.isBlank()) nombreErrors.add(FieldError.Required("nombre"))
        else if (nombre.length > 50)
            nombreErrors.add(FieldError.TooLong("nombre", 50))
        if (nombreErrors.isNotEmpty()) errors["nombre"] = nombreErrors

        // descripcion
        val descripcionErrors = mutableListOf<FieldError>()
        val descripcion = input.descripcion?.trim()
        if (descripcion != null && descripcion.length > 511)
            descripcionErrors.add(FieldError.TooLong("descripcion", 511))
        if (descripcionErrors.isNotEmpty()) errors["descripcion"] = descripcionErrors

        // genero
        if (input.genero == null)
            errors["genero"] = listOf(FieldError.Required("genero"))

        // url (opcional)
        val urlErrors = mutableListOf<FieldError>()
        val url = input.url?.trim()
        if (url != null) {
            if (url.length > 511) urlErrors.add(FieldError.TooLong("url", 511))
            if (url.isNotEmpty() && !url.startsWith("http://") && !url.startsWith("https://"))
                urlErrors.add(FieldError.InvalidFormat("url", "http:// o https://"))
        }
        if (urlErrors.isNotEmpty()) errors["url"] = urlErrors

        // duracion: HH:mm
        if (!isValidHourMinute(input.duracion))
            errors["duracion"] = listOf(FieldError.InvalidFormat("duracion", "HH:mm"))

        // edad
        if (input.edad != null && input.edad < 0)
            errors["edad"] = listOf(FieldError.Custom("La edad debe ser 0 o mayor."))

        // participantes
        input.participantes.forEachIndexed { index, p ->
            val pIdKey = "participantes[$index].id"
            if (p.id == null || p.id <= 0)
                errors[pIdKey] = listOf(FieldError.Custom("El ID del participante debe ser positivo."))
            val pRolesKey = "participantes[$index].roles"
            if (p.roles.isEmpty())
                errors[pRolesKey] = listOf(FieldError.Custom("El participante debe tener al menos un rol."))
        }

        return errors
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
