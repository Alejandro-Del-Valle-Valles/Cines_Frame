package com.alejandro.proyecto_cines_frame.domain.model
enum class MovieStatus { CARTELERA, ESTRENO, INACTIVA } //No lo pongo en otro archivo, creo que quedara mas mejor tenerlo aqui para que sea accesible
//Cambiar a boolean, 1 Esta en cartelera, 0 en proximos estrenos, null la pelicula no esta activa actualmente.
//Creo que el enum es mas claro que el boolean, pero tu decides aqui delegado,voy mantener el enum por ahora (por legibilidad)
data class Movie(
    val id: Int,
    val title: String,
    val status: MovieStatus,
    val posterResName: String? = null,
    val sessions: List<Session> = emptyList(),
    val genres: List<MovieGenre> = emptyList()
)