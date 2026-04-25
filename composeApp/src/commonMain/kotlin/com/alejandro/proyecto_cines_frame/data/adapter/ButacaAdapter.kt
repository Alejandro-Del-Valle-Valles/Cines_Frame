package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.ButacaDTO
import com.alejandro.proyecto_cines_frame.domain.model.Butaca

object ButacaAdapter {

    fun toButaca(butacaDTO: ButacaDTO, ocupada: Boolean = false) : Butaca = Butaca(
        fila = butacaDTO.fila,
        butaca = butacaDTO.butaca,
        ocupada = ocupada
    )
}