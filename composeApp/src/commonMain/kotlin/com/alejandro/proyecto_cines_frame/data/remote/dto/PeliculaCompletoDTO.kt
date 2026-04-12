package com.alejandro.proyecto_cines_frame.data.remote.dto

import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero

import kotlinx.serialization.Serializable

@Serializable
data class PeliculaCompletoDTO(
    val id: String,
    val nombre: String,
    val descripcion: String?,
    val genero: PeliculaGenero,
    val url: String?,
    val duracion: String,
    val edad: Int?,
    val enCartelera: Boolean?,
    val participantes: List<ParticipanteCompletoDTO> = mutableListOf()
)