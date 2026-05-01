package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.ProductoDTO
import com.alejandro.proyecto_cines_frame.domain.model.Producto

fun ProductoDTO.toDomain() =
    Producto(
        nombre = nombre,
        precio = precio,
        stock = stock,
        alergenos = alergenos?.map { it.toDomain() }?.toSet()
            ?: setOf()
    )
