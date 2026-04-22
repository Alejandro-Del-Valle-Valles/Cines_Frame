package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO
import com.alejandro.proyecto_cines_frame.domain.model.Baner

object BanerAdapter {

    fun toBaner(dto: BanerDTO) : Baner = Baner(
        peliculaId = dto.peliculaId,
        url = dto.url,
        empeiza = DateAdater.toLocalDate(dto.empieza),
        termina = DateAdater.toLocalDate(dto.termina)
    )

}