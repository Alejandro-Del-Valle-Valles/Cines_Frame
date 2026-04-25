package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HoldButacaDTO(
    val numSala: Int,
    val peliculaId: String,
    val horario: String
)
