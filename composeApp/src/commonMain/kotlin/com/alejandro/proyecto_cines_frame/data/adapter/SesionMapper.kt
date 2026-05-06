package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCompletaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionDTO
import com.alejandro.proyecto_cines_frame.domain.extension.toLocalDateTime
import com.alejandro.proyecto_cines_frame.domain.model.Sesion

fun SesionDTO.toDomain(pelicula: PeliculaDTO) : Sesion =
    Sesion(
        numSala = numSala,
        pelicula = pelicula.toDomain(),
        horario = horario.toLocalDateTime(),
        tresD = tresD,
        vose = vose
    )

fun SesionCompletaDTO.toDomain() : Sesion =
    Sesion(
        numSala = numSala,
        pelicula = pelicula.toDomain(),
        horario = horario.toLocalDateTime(),
        tresD = tresD,
        vose = vose
    )

