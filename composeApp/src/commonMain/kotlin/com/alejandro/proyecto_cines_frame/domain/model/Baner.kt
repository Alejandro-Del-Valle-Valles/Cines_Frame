package com.alejandro.proyecto_cines_frame.domain.model

import kotlinx.datetime.LocalDate

data class Baner (
    val peliculaId: String,
    val url: String,
    val empeiza: LocalDate,
    val termina: LocalDate
)