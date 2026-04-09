package com.alejandro.proyecto_cines_frame.ui.components.features.movies

import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

object MovieMockData {
    private val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    fun getSesiones(): List<Sesion> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val date = MovieMockData.now.date.plus(now.day, DateTimeUnit.DAY)
        val peliculaBase = Pelicula(
            id = "p1",
            nombre = "Película Mock",
            descripcion = "Descripción de prueba",
            estado = PeliculaEstado.CARTELERA,
            portada = null,
            duracion = LocalTime(1, 45), // 1h 45m
            genero = PeliculaGenero.ACCION
        )

        return listOf(
            Sesion(numSala = 1, pelicula = peliculaBase, horario = LocalDateTime(date.year, date.month, date.day, 10, 0)),
            Sesion(numSala = 1, pelicula = peliculaBase, horario = LocalDateTime(date.year, date.month, date.day, 12, 30)),
            Sesion(numSala = 2, pelicula = peliculaBase, horario = LocalDateTime(date.year, date.month, date.day, 15, 0)),
            Sesion(numSala = 2, pelicula = peliculaBase, horario = LocalDateTime(date.year, date.month, date.day, 17, 30)),
            Sesion(numSala = 3, pelicula = peliculaBase, horario = LocalDateTime(date.year, date.month, date.day, 20, 0)),
            Sesion(numSala = 3, pelicula = peliculaBase, horario = LocalDateTime(date.year, date.month, date.day, 22, 30)),
        )
    }
}