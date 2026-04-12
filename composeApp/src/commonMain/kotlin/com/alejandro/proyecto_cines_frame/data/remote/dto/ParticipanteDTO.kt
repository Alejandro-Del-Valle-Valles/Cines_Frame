package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ParticipanteDTO(
    val id: Int,
    val nombre: String
)
