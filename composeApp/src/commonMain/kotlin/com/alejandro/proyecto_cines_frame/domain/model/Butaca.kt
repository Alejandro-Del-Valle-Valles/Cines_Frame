package com.alejandro.proyecto_cines_frame.domain.model

data class Butaca(
    val fila: Int,
    val butaca: Int,
    val ocupada: Boolean = false
)
