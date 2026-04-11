package com.alejandro.proyecto_cines_frame.data.remote.dto

import com.alejandro.proyecto_cines_frame.domain.enums.CuentaRol
import kotlinx.serialization.Serializable

@Serializable
data class CuentaDTO(
    val correo: String,
    val nombre: String,
    val contrasena: String,
    val rol: CuentaRol
)
