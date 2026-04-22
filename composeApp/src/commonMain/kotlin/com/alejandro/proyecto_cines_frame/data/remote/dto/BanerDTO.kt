package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BanerDTO(
    val peliculaId: String,
    val url: String,
    val empieza: String,
    val termina: String
)
