package com.alejandro.proyecto_cines_frame.domain.model
enum class MovieStatus { CARTELERA, ESTRENO, INACTIVA } //Enum aquí, en la BBDD se guarda como boolean.
data class Movie(
    val id: Int,
    val title: String,
    val status: MovieStatus,
    val posterResName: String? = null,
    val sessions: List<Session> = emptyList(),
    val genres: List<MovieGenre> = emptyList()
)