package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.domain.model.Usuario

object UsuarioAdapter {

    fun toUsuario(correo: String) : Usuario = Usuario(correo)
}