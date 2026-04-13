package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteCompletoDTO
import com.alejandro.proyecto_cines_frame.domain.model.Credito

object CreditoAdapter {

    fun toCredito(participante: ParticipanteCompletoDTO) =
        Credito(
            participante = ParticipanteAdpater.toParticipante(participante),
            roles = participante.roles.toSet()
        )
}