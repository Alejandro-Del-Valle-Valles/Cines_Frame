package com.alejandro.proyecto_cines_frame.domain.model

class Compra(
    val id: String = "",
    val usuario: Usuario,
    val lineasCompra: Set<LineaCompra>
) {
}