package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.domain.model.Usuario

object UsuarioMapper {

    fun toUsuario(correo: String) = Usuario(correo)
}