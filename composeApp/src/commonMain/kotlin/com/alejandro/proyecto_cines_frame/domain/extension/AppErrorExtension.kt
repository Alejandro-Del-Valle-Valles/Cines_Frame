package com.alejandro.proyecto_cines_frame.domain.extension

import com.alejandro.proyecto_cines_frame.core.error.AppError

fun AppError.toFieldErrorMessagesIfAny(): Map<String, String> =
    if (this is AppError.Validation) this.fields else emptyMap()