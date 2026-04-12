package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CompraDTO(
    val correo: String,
    val lineasCompra: List<LineaCompraDTO> = mutableListOf()
)
