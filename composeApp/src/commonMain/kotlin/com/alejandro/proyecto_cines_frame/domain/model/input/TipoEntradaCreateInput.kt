package com.alejandro.proyecto_cines_frame.domain.model.input

data class TipoEntradaCreateInput(
    val id: Int?,
    val nombre: String,
    val descripcion: String,
    val precio: Float
)
