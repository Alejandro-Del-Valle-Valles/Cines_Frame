package com.alejandro.proyecto_cines_frame.domain.model

abstract class LineaCompra(
    val numLinea: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LineaCompra

        return numLinea == other.numLinea
    }

    override fun hashCode(): Int {
        return numLinea
    }
}