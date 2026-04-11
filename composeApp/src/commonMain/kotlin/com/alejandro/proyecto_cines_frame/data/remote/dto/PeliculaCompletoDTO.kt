package com.alejandro.proyecto_cines_frame.data.remote.dto

import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero

import kotlinx.serialization.Serializable

@Serializable
class PeliculaCompletoDTO(
    id: String,
    nombre: String,
    descripcion: String?,
    genero: PeliculaGenero,
    url: String?,
    duracion: String,
    edad: Int?,
    enCartelera: Boolean?,
    val participantes: List<ParticipanteCompletoDTO> = mutableListOf()
) : PeliculaDTO(id, nombre, descripcion, genero, url, duracion, edad, enCartelera){
}