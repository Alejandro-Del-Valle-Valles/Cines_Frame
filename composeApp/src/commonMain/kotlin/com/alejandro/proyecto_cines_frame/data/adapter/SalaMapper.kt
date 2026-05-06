package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.SalaDTO
import com.alejandro.proyecto_cines_frame.domain.model.Sala


fun SalaDTO.toDomain() = Sala(numero = numero, aforo = aforo)
