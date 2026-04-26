package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HoldButacaRequest(
    val token: String,
    val fila: Int,
    val butaca: Int
)
