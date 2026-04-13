package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.ProductoDTO
import com.alejandro.proyecto_cines_frame.domain.model.Alergeno
import com.alejandro.proyecto_cines_frame.domain.model.Producto

object ProductoAdapter {

    fun toProducto(producto: ProductoDTO) =
        Producto(
            nombre = producto.nombre,
            precio = producto.precio,
            stock = producto.stock,
            alergenos = producto.alergenos?.map { AlergenoAdapter.toAlergeno(it) }?.toSet()
                ?: setOf()
        )
}