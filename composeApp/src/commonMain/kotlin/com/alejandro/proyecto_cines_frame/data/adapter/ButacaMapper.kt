package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.ButacaDTO
import com.alejandro.proyecto_cines_frame.domain.model.Butaca


fun ButacaDTO.toDomain(ocupada: Boolean = false) : Butaca = Butaca(
    fila = fila,
    butaca = butaca,
    ocupada = ocupada
)
