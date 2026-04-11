package com.alejandro.proyecto_cines_frame.domain.model

class LineaCompraProducto(
    numLinea: Int,
    val producto: Producto
) : LineaCompra(numLinea) {
}