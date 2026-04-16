package com.alejandro.proyecto_cines_frame.domain.extension

import com.alejandro.proyecto_cines_frame.domain.validation.FieldErrors
import com.alejandro.proyecto_cines_frame.domain.validation.FieldError

fun FieldError.toMessage(): String = when (this) {
    is FieldError.Required -> "Campo obligatorio"
    is FieldError.InvalidFormat -> "Formato inválido. Se espera: $expectedFormat"
    is FieldError.TooShort -> "Mínimo $minLength caracteres"
    is FieldError.TooLong -> "Máximo $maxLength caracteres"
    is FieldError.Custom -> message
}

fun FieldErrors.toFirstUiMessagePerField(): Map<String, String> =
    mapValues { (_, errors) ->
        errors.firstOrNull()?.toMessage() ?: ""
    }.filterValues { it.isNotBlank() }

fun FieldErrors.toUiMessagesPerField(): Map<String, List<String>> =
    mapValues { (_, errors) -> errors.map { it.toMessage() } }