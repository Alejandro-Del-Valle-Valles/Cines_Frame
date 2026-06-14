package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.CodigoDescuentoDTO
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento

fun CodigoDescuentoDTO.toDomain(): CodigoDescuento =
    CodigoDescuento(
        id = id,
        codigo = codigo,
        condicion = condicion,
        porcentaje = porcentaje,
        activo = activo
    )

fun CodigoDescuento.toDto(): CodigoDescuentoDTO =
    CodigoDescuentoDTO(
        id = id,
        codigo = codigo,
        condicion = condicion,
        porcentaje = porcentaje,
        activo = activo
    )