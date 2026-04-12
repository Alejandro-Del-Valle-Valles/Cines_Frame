package com.alejandro.proyecto_cines_frame.domain.enums

import kotlinx.serialization.Serializable

@Serializable
enum class CuentaRol {
    CLIENTE, EMPLEADO, ADMINISTRADOR
}