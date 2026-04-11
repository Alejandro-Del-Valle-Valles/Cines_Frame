package com.alejandro.proyecto_cines_frame.domain.model.input

import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero

data class PeliculaCreateInput(
    val nombre: String,
    val descripcion: String? = null,
    val genero: PeliculaGenero?,
    val url: String? = null,
    val duracion: String, // "HH:mm"
    val edad: Int? = null,
    val enCartelera: Boolean? = null,
    val participantes: List<ParticipanteCreateInput> = emptyList()
)