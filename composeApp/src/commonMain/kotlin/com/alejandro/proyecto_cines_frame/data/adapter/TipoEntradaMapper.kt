package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.TipoEntradaDTO
import com.alejandro.proyecto_cines_frame.domain.model.TipoEntrada

fun TipoEntradaDTO.toDomain() = TipoEntrada(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    precio = precio
)