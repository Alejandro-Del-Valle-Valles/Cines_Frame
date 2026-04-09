package com.alejandro.proyecto_cines_frame.domain.model

class Producto(
    val nombre: String,
    val precio: Float,
    val stock: Int,
    val alergenos: Set<Alergeno> = setOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Producto

        return nombre == other.nombre
    }

    override fun hashCode(): Int = nombre.hashCode()
}
