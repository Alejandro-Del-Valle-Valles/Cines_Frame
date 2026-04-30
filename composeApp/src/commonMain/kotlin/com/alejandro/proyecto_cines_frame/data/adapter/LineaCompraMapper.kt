package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraEntradaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraProductoCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraProductoDTO
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraEntrada
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraProducto
import com.alejandro.proyecto_cines_frame.domain.model.Producto

fun LineaCompraDTO.toDomain() =
    when (this) {
        is LineaCompraEntradaDTO ->
            LineaCompraEntrada(
                numLinea = numero,
                entrada = entrada.toDomain()
            )
        is LineaCompraProductoDTO ->
            LineaCompraProducto(
                numLinea = numero,
                producto = producto.toDomain()
            )
        is LineaCompraProductoCreateDTO ->
            LineaCompraProducto(
                numLinea = numero,
                producto = Producto(
                    nombre = nombreProducto,
                    precio = 0f,
                    stock = 0
                )
            )
    }