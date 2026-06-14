package com.alejandro.proyecto_cines_frame.data.remote.dto

import com.alejandro.proyecto_cines_frame.domain.enums.DescuentoCondicion
import kotlinx.serialization.Serializable

@Serializable
data class CodigoDescuentoCreateDTO (
    val codigo: String,
    val condicion: DescuentoCondicion,
    val porcentaje: Int,
    val activo: Boolean
)