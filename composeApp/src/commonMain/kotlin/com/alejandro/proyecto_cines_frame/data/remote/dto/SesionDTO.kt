package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SesionDTO(
    val numSala: Int,
    val tresD: Boolean = false,
    val vose: Boolean = false,
    val peliculaId: String,
    val horario: String
)