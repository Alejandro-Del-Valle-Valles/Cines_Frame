package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.AlergenoDTO
import com.alejandro.proyecto_cines_frame.domain.model.Alergeno

object AlergenoAdapter {

    fun toAlergeno(alergeno: AlergenoDTO) = Alergeno(nombre = alergeno.nombre)
}