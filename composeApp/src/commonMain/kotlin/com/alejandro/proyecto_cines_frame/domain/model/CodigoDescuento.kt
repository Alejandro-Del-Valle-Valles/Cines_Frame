package com.alejandro.proyecto_cines_frame.domain.model

import com.alejandro.proyecto_cines_frame.domain.enums.DescuentoCondicion
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import kotlinx.datetime.LocalTime

class CodigoDescuento (
    val id: Int = 0 ,
    val codigo: String,
    val condicion: DescuentoCondicion,
    val porcentajeDescuento: Int,
    val activo: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CodigoDescuento

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}