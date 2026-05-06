package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteCompletoDTO
import com.alejandro.proyecto_cines_frame.domain.model.Credito


fun ParticipanteCompletoDTO.toCreditoDomain() =
    Credito(
        participante = toDomain(),
        roles = roles.toSet()
    )
