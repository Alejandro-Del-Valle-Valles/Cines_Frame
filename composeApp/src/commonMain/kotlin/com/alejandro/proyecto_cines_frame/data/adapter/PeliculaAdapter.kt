package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.converter.PeliculaEstadoConverter
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCompletoDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaDTO
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import kotlinx.datetime.LocalTime

object PeliculaAdapter {

    fun toPelicula(peliculaDTO: PeliculaDTO) : Pelicula {
        return Pelicula(
            id = peliculaDTO.id,
            nombre = peliculaDTO.nombre,
            descripcion = peliculaDTO.descripcion ?: "Sin descripción",
            estado = PeliculaEstadoConverter.toPeliculaEstado(peliculaDTO.enCartelera),
            portada = peliculaDTO.url,
            duracion = DateAdater.toLocalTime(peliculaDTO.duracion),
            calificacionEdad = peliculaDTO.edad ?: 0,
            genero = peliculaDTO.genero
        )
    }

    fun toPelicula(peliculaCompletoDTO: PeliculaCompletoDTO) : Pelicula {
        return Pelicula(
            id = peliculaCompletoDTO.id,
            nombre = peliculaCompletoDTO.nombre,
            descripcion = peliculaCompletoDTO.descripcion ?: "Sin descripción",
            estado = PeliculaEstadoConverter.toPeliculaEstado(peliculaCompletoDTO.enCartelera),
            portada = peliculaCompletoDTO.url,
            duracion = DateAdater.toLocalTime(peliculaCompletoDTO.duracion),
            calificacionEdad = peliculaCompletoDTO.edad ?: 0,
            genero = peliculaCompletoDTO.genero,
            creditos = peliculaCompletoDTO.participantes.map { CreditoAdapter.toCredito(it) }
        )
    }
}