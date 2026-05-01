package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteCompletoDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteDTO
import com.alejandro.proyecto_cines_frame.domain.model.Participante


fun ParticipanteDTO.toDomain() =
    Participante(
        id = id,
        nombre = nombre
    )

fun ParticipanteCompletoDTO.toDomain() =
    Participante(
        id = id,
        nombre = nombre
    )
