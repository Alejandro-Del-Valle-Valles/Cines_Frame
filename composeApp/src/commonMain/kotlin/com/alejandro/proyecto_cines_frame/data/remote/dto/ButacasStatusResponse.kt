package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ButacasStatusResponse(
    val ocupadas: List<ButacaDTO>,
    val bloqueadas: List<ButacaDTO>
)
