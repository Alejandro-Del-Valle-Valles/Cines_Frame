package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO
import com.alejandro.proyecto_cines_frame.domain.extension.toLocalDate
import com.alejandro.proyecto_cines_frame.domain.model.Baner


fun BanerDTO.toDomain() : Baner = Baner(
    id = id,
    peliculaId = peliculaId,
    url = url,
    empeiza = empieza.toLocalDate(),
    termina = termina.toLocalDate()
)

