package com.alejandro.proyecto_cines_frame.ui.components.filter

data class FilterState(
    val selectedDay: FilterDay? = null,
    val is3D: Boolean? = null,
    val isVOSE: Boolean = false
)