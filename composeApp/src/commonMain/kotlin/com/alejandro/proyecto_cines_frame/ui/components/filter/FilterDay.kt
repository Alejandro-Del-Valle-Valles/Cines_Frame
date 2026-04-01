package com.alejandro.proyecto_cines_frame.ui.components.filter

import kotlinx.datetime.LocalDate

data class FilterDay(
    val date: LocalDate,
    val label: String
)