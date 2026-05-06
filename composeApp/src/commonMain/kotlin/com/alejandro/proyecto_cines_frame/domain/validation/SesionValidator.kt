package com.alejandro.proyecto_cines_frame.domain.validation

import com.alejandro.proyecto_cines_frame.domain.model.input.SesionCreateInput
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * Validador de creación de sesiones
 */
object SesionValidator {

        /**
        * Valida los datos de la sesión, y los que sean erróneos, genera un mapa de errores
        */
        fun validateCreate(input: SesionCreateInput): FieldErrors {
            val errors = mutableMapOf<String, List<FieldError>>()

            // peliculaId
            if (input.peliculaId.isBlank())
                errors["peliculaId"] = listOf(FieldError.Required("El ID de la película no puede estar vacío."))

            // salaId
            if (input.numSala <= 0)
                errors["salaId"] = listOf(FieldError.Custom("El número de la sala no puede ser iferior a 1"))

            //horario
            if(input.horario <= Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) )
                errors["horario"] = listOf(FieldError.Custom("El horario debe ser en el futuro."))

            return errors
        }
}