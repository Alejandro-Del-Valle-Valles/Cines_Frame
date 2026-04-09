package com.alejandro.proyecto_cines_frame.domain.model

class Sala(
    val numero: Int,
    val aforo: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sala

        return numero == other.numero
    }

    override fun hashCode(): Int = numero
}
