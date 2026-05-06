package com.alejandro.proyecto_cines_frame.domain.model.input

import kotlinx.datetime.LocalDate

data class BanerCreateInput(
    val peliculaId: String,
    val antiguaUrl: String? = null,
    val url: String,
    val empieza: LocalDate,
    val termina: LocalDate
)
