package com.alejandro.proyecto_cines_frame.domain.model

import kotlinx.datetime.LocalDateTime

class Entrada(
    val numSala: Int,
    val peliculaId: String,
    val horario: LocalDateTime,
    val fila: Int,
    val butaca: Int,
    val tipo: TipoEntrada
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entrada

        if (numSala != other.numSala) return false
        if (fila != other.fila) return false
        if (butaca != other.butaca) return false
        if (peliculaId != other.peliculaId) return false
        if (horario != other.horario) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numSala
        result = 31 * result + fila
        result = 31 * result + butaca
        result = 31 * result + peliculaId.hashCode()
        result = 31 * result + horario.hashCode()
        return result
    }
}