package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.CompraDTO
import com.alejandro.proyecto_cines_frame.domain.model.Compra

fun CompraDTO.toDomain() =
    Compra(
        usuario = UsuarioMapper.toUsuario(correo),
        lineasCompra = lineasCompra.map { it.toDomain() }.toSet()
    )
