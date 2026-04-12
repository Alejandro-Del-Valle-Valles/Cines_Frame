package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductoDTO(
    val nombre: String,
    val precio: Float,
    val stock: Int,
    val alergenos: List<AlergenoDTO>?
)
