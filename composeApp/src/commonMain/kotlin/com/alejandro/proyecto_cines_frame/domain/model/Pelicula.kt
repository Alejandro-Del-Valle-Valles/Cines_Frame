package com.alejandro.proyecto_cines_frame.domain.model

import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import kotlinx.datetime.LocalTime

class Pelicula(
    val id: String = "",
    val nombre: String,
    val descripcion: String,
    val estado: PeliculaEstado,
    val portada: String?,
    val duracion: LocalTime,
    val calificacionEdad: Int? = 0,
    val genero: PeliculaGenero,
    val creditos: List<Credito> = listOf(),
    val sesiones: List<Sesion> = listOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pelicula

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}