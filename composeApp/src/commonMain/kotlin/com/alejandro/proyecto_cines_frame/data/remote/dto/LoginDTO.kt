package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO(
    val correo: String,
    val contrasena: String
)
