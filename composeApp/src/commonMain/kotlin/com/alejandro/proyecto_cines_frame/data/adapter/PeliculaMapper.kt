package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.converter.PeliculaEstadoConverter
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCompletoAndSesionesDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCompletoDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaDTO
import com.alejandro.proyecto_cines_frame.domain.extension.toLocalTime
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula


fun PeliculaDTO.toDomain() : Pelicula =
    Pelicula(
        id = id,
        nombre = nombre,
        descripcion = descripcion ?: "Sin descripción",
        estado = PeliculaEstadoConverter.toPeliculaEstado(enCartelera),
        portada = url,
        duracion = duracion.toLocalTime(),
        calificacionEdad = edad ?: 0,
        genero = genero
    )


fun PeliculaCompletoDTO.toDomain() : Pelicula =
    Pelicula(
        id = id,
        nombre = nombre,
        descripcion = descripcion ?: "Sin descripción",
        estado = PeliculaEstadoConverter.toPeliculaEstado(enCartelera),
        portada = url,
        duracion = duracion.toLocalTime(),
        calificacionEdad = edad ?: 0,
        genero = genero,
        creditos = participantes.map { it.toCreditoDomain() }
    )

fun PeliculaCompletoAndSesionesDTO.toDomain() : Pelicula =
    Pelicula(
        id = id,
        nombre = nombre,
        descripcion = descripcion ?: "Sin descripción",
        estado = PeliculaEstadoConverter.toPeliculaEstado(enCartelera),
        portada = url,
        duracion = duracion.toLocalTime(),
        calificacionEdad = edad ?: 0,
        genero = genero,
        creditos = participantes.map { it.toCreditoDomain() },
        sesiones = sesiones.map { it.toDomain(this.toDto()) }
    )

fun PeliculaCompletoAndSesionesDTO.toDto() =
    PeliculaDTO(
        id = id,
        nombre = nombre,
        descripcion = descripcion ?: "Sin descripción",
        genero = genero,
        url = url,
        duracion = duracion,
        edad = edad ?: 0,
        enCartelera = enCartelera
    )

