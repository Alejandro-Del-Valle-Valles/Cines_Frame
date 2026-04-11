package com.alejandro.proyecto_cines_frame.data.remote.dto

import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import kotlinx.serialization.Serializable

@Serializable
open class PeliculaDTO(
    val id: String,
    val nombre: String,
    val descripcion: String?,
    val genero: PeliculaGenero,
    val url: String?,
    val duracion: String,
    val edad: Int?,
    val enCartelera: Boolean?
) {

}