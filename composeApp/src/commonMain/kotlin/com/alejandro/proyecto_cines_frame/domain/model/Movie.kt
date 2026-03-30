package com.alejandro.proyecto_cines_frame.domain.model
enum class MovieStatus { CARTELERA, ESTRENO, INACTIVA } //No lo pongo en otro archivo, creo que quedara mas mejor tenerlo aqui para que sea accesible
//Cambiar a boolean, 1 Esta en cartelera, 0 en proximos estrenos, null la pelicula no esta activa actualmente.
data class Movie(
    val id: Int,
    val title: String,
    val status: MovieStatus, //Mi idea era un boolean, pero asi podremos decidir si la pelicula va al apartado de cartelera, esta inactiva o esta en cartelera.
    val posterResName: String? = null
)
