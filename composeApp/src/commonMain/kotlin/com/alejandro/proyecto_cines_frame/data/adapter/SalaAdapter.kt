package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.SalaDTO
import com.alejandro.proyecto_cines_frame.domain.model.Sala

object SalaAdapter {

    fun toSala(sala: SalaDTO) = Sala(numero = sala.numero, aforo = sala.aforo)
}