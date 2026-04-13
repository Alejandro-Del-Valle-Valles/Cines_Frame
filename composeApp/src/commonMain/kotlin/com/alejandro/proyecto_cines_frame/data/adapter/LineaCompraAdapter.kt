package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraEntradaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraProductoCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraProductoDTO
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraEntrada
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraProducto

object LineaCompraAdapter {

    fun toLineaCompra(dto: LineaCompraDTO) =
        when (dto) {
            is LineaCompraEntradaDTO ->
                LineaCompraEntrada(
                    numLinea = dto.numero,
                    entrada = EntradaAdapter.toEntrada(dto.entrada)
                )
            is LineaCompraProductoDTO ->
                LineaCompraProducto(
                    numLinea = dto.numero,
                    producto = ProductoAdapter.toProducto(dto.producto)
                )
            is LineaCompraProductoCreateDTO -> {
                error("No existe modelo de dominio para LineaCompraProductoCreateDTO (nombreProducto=${dto.nombreProducto})")
            }
        }
}