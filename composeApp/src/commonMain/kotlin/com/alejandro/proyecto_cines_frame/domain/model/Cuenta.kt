package com.alejandro.proyecto_cines_frame.domain.model

import com.alejandro.proyecto_cines_frame.domain.enums.CuentaRol

data class Cuenta(
    val correo: String,
    val contrasena: String,
    val nombre: String,
    val rol: CuentaRol
)
