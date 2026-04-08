package com.alejandro.proyecto_cines_frame.ui.components.filter
//Estado actual de los filtros awo
data class FilterState(
    val selectedDay: FilterDay? = null,
    val is3D: Boolean? = null,
    val isVOSE: Boolean = false
)