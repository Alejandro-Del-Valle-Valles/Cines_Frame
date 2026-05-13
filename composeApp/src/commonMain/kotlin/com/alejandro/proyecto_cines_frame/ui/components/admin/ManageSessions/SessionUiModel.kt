package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

import kotlinx.datetime.LocalDateTime

data class SessionUiModel(
    val peliculaId: String,
    val peliculaNombre: String,
    val numSala: Int,
    val horario: LocalDateTime,
    val tresD: Boolean = false,
    val vose: Boolean = false
)