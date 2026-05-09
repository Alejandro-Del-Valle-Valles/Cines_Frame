package com.alejandro.proyecto_cines_frame.domain.model.input

import kotlinx.datetime.LocalDateTime

data class SesionCreateInput(
    val numSala: Int,
    val tresD: Boolean,
    val vose: Boolean,
    val peliculaId: String,
    val horario: LocalDateTime
)
