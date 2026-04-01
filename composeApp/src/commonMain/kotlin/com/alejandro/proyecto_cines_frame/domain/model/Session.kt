package com.alejandro.proyecto_cines_frame.domain.model

import kotlinx.datetime.LocalDateTime

data class Session(
    val dateTime: LocalDateTime,
    val is3D: Boolean = false,
    val isVOSE: Boolean = false
)