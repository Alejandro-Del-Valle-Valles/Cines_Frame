package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaLoginDTO
import com.alejandro.proyecto_cines_frame.domain.model.Cuenta

object CuentaAdapter {

    /**
     * No settea Token
     */
    fun toCuenta(cuenta: CuentaDTO) =
        Cuenta(
            usuario = UsuarioAdapter.toUsuario(cuenta.correo),
            contrasena = cuenta.contrasena,
            nombre = cuenta.nombre,
            rol = cuenta.rol
        )

    /**
     * Settea token pero no contraseña
     */
    fun toCuenta(cuenta: CuentaLoginDTO) =
        Cuenta(
            usuario = UsuarioAdapter.toUsuario(cuenta.correo),
            contrasena = "",
            nombre = cuenta.nombre,
            rol = cuenta.rol,
            token = cuenta.token
        )
}