package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.EntradaDTO
import com.alejandro.proyecto_cines_frame.domain.model.Entrada
import kotlinx.datetime.LocalDateTime

object EntradaAdapter {

    fun toEntrada(entrada: EntradaDTO) =
        Entrada(
            numSala = entrada.sesion.numSala,
            peliculaId =  entrada.sesion.peliculaId,
            horario = LocalDateTime.parse(entrada.sesion.horario),
            fila = entrada.numFila,
            butaca = entrada.numButaca,
            precio = entrada.precio
        )
}