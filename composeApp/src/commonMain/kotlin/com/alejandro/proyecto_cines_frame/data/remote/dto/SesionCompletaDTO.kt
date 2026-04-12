package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SesionCompletaDTO(
    val numSala: Int,
    val tresD: Boolean,
    val vose: Boolean,
    val pelicula: PeliculaDTO,
    val horario: String
)
