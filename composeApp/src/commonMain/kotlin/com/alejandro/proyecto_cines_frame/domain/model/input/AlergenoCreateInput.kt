package com.alejandro.proyecto_cines_frame.domain.model.input

data class AlergenoCreateInput(
    val nombre: String,
    val antiguoNombre: String? = null
)
