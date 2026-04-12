package com.alejandro.proyecto_cines_frame.domain.model

import kotlinx.datetime.LocalDateTime

class Sesion(
    val numSala: Int,
    val pelicula: Pelicula,
    val horario: LocalDateTime,
    val tresD: Boolean = false,
    val vose: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sesion

        if (numSala != other.numSala) return false
        if (pelicula != other.pelicula) return false
        if (horario != other.horario) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numSala
        result = 31 * result + pelicula.hashCode()
        result = 31 * result + horario.hashCode()
        return result
    }
}