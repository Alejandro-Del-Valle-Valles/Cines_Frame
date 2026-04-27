package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraEntradaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraProductoCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraProductoDTO
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraEntrada
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraProducto
import com.alejandro.proyecto_cines_frame.domain.model.Producto

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
            is LineaCompraProductoCreateDTO ->
                // En createCompra el backend puede devolver la misma línea de request sin detalle de producto.
                LineaCompraProducto(
                    numLinea = dto.numero,
                    producto = Producto(
                        nombre = dto.nombreProducto,
                        precio = 0f,
                        stock = 0
                    )
                )
        }
}