package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaLoginDTO
import com.alejandro.proyecto_cines_frame.domain.model.Cuenta


/**
 * No settea Token
 */
fun CuentaDTO.toDomain() =
    Cuenta(
        usuario = UsuarioMapper.toUsuario(correo),
        contrasena = contrasena,
        nombre = nombre,
        rol = rol
    )

/**
 * Settea token pero no contraseña
 */
fun CuentaLoginDTO.toDomain() =
    Cuenta(
        usuario = UsuarioMapper.toUsuario(correo),
        contrasena = "",
        nombre = nombre,
        rol = rol,
        token = token
    )