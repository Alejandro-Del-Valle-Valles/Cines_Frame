package com.alejandro.proyecto_cines_frame.domain.model.input

import com.alejandro.proyecto_cines_frame.domain.enums.DescuentoCondicion
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero

data class CodigoDescuentoCreateInput (
    val codigo: String,
    val condicion: DescuentoCondicion,//? = DescuentoCondicion.TODOS,
    val porcentaje: Int,
    val activo: Boolean
)