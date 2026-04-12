package com.alejandro.proyecto_cines_frame.ui.components.filter

import kotlinx.datetime.LocalDate
//Un dia disponible para filtrar
data class FilterDay(
    val date: LocalDate,
    val label: String
)
