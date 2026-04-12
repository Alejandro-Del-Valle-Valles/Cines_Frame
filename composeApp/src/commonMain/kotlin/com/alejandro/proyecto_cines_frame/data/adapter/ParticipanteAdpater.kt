package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteCompletoDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteDTO
import com.alejandro.proyecto_cines_frame.domain.model.Participante

object ParticipanteAdpater {

    fun toParticipante(participante: ParticipanteDTO) =
        Participante(
            id = participante.id,
            nombre = participante.nombre
        )

    fun toParticipante(participante: ParticipanteCompletoDTO) =
        Participante(
            id = participante.id,
            nombre = participante.nombre
        )
}