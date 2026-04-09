package com.alejandro.proyecto_cines_frame.domain.model

import com.alejandro.proyecto_cines_frame.domain.enums.ParticipanteRol

class Credito(
    val participante: Participante,
    val roles: Set<ParticipanteRol>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Credito

        if (participante != other.participante) return false
        if (roles != other.roles) return false

        return true
    }

    override fun hashCode(): Int {
        var result = participante.hashCode()
        result = 31 * result + roles.hashCode()
        return result
    }
}
