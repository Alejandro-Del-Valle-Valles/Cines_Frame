package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCompletaDTO
import com.alejandro.proyecto_cines_frame.domain.extension.toLocalDateTime
import com.alejandro.proyecto_cines_frame.domain.model.Sesion

object SesionAdapter {

    fun toSesion(sesion: SesionCompletaDTO) : Sesion {
        return Sesion(
            numSala = sesion.numSala,
            pelicula = PeliculaAdapter.toPelicula(sesion.pelicula),
            horario = sesion.horario.toLocalDateTime(),
            tresD = sesion.tresD,
            vose = sesion.vose
        )
    }
}