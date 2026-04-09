package com.alejandro.proyecto_cines_frame.domain.model

class Participante(
    val id: Int = 0,
    val nombre: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Participante

        return id == other.id
    }

    override fun hashCode(): Int = id
}
