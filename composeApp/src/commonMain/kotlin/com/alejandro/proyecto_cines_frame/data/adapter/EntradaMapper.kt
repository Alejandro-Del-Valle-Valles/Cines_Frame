package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.EntradaDTO
import com.alejandro.proyecto_cines_frame.domain.model.Entrada
import kotlinx.datetime.LocalDateTime


fun EntradaDTO.toDomain() =
        Entrada(
            numSala = sesion.numSala,
            peliculaId =  sesion.peliculaId,
            horario = LocalDateTime.parse(sesion.horario),
            fila = numFila,
            butaca = numButaca,
            tipo = tipo.toDomain()
        )