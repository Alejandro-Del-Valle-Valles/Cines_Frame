package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateTipoEntrada(
    val nombre: String,
    val descripcion: String,
    val precio: Float
)
