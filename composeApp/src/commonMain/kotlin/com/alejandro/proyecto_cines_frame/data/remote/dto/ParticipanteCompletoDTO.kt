package com.alejandro.proyecto_cines_frame.data.remote.dto

import com.alejandro.proyecto_cines_frame.domain.enums.ParticipanteRol
import kotlinx.serialization.Serializable

@Serializable
class ParticipanteCompletoDTO(
    id: Int,
    nombre: String,
    val roles: List<ParticipanteRol> = mutableListOf()
) : ParticipanteDTO(id, nombre)
