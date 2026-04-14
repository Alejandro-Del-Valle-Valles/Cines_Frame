package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.CompraDTO
import com.alejandro.proyecto_cines_frame.domain.model.Compra

object CompraAdapter {

    fun toCompra(compra: CompraDTO) =
        Compra(
            usuario = UsuarioAdapter.toUsuario(compra.correo),
            lineasCompra = compra.lineasCompra.map { LineaCompraAdapter.toLineaCompra(it) }.toSet()
        )
}