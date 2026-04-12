package com.alejandro.proyecto_cines_frame.data.remote.dto

import com.alejandro.proyecto_cines_frame.domain.enums.ParticipanteRol
import kotlinx.serialization.Serializable

@Serializable
data class ParticipanteCompletoDTO(
    val id: Int,
    val nombre: String,
    val roles: List<ParticipanteRol> = mutableListOf()
)
