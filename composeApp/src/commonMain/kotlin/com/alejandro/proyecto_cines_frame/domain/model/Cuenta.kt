package com.alejandro.proyecto_cines_frame.domain.model

import com.alejandro.proyecto_cines_frame.domain.enums.CuentaRol

class Cuenta(
    val usuario: Usuario,
    val contrasena: String,
    val nombre: String,
    val rol: CuentaRol,
    val token: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cuenta

        return usuario == other.usuario
    }

    override fun hashCode(): Int {
        return usuario.hashCode()
    }
}
