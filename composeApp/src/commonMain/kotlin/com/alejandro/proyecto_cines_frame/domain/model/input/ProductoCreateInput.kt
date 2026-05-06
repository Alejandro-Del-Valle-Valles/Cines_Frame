package com.alejandro.proyecto_cines_frame.domain.model.input

data class ProductoCreateInput(
    val antiguoNombre: String? = null,
    val nombre: String,
    val precio: Float,
    val stock: Int,
    val alergenos: List<AlergenoCreateInput> = emptyList()
)