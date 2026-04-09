package com.alejandro.proyecto_cines_frame.domain.model

import kotlinx.datetime.LocalDateTime

data class Sesion(
    val sala: Sala,
    val pelicula: Pelicula,
    val horario: LocalDateTime
)