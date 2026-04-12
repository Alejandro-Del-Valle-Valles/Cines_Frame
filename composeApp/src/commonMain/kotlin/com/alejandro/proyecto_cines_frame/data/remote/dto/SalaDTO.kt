package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SalaDTO(
    val numero: Int,
    val aforo: Int
)
