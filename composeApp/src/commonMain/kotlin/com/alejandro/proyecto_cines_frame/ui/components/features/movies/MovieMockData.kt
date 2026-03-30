package com.alejandro.proyecto_cines_frame.ui.components.features.movies

import com.alejandro.proyecto_cines_frame.domain.model.Movie
import com.alejandro.proyecto_cines_frame.domain.model.MovieStatus
import com.alejandro.proyecto_cines_frame.domain.model.Session
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
//Para añadir las pelis, esto esta muy prueba prueboso, but funciona

object MovieMockData {
    private val now = Clock.System.now().toLocalDateTime(TimeZone.Companion.currentSystemDefault())

    private fun s(h: Int, m: Int, d: Int = 0): Session {
        val date = now.date.plus(d, DateTimeUnit.Companion.DAY)
        return Session(LocalDateTime(date.year, date.month, date.dayOfMonth, h, m))
    }

    fun getMovies(): List<Movie> = listOf(
        Movie(
            1,
            "Película A",
            MovieStatus.CARTELERA,
            sessions = listOf(s(16, 30), s(18, 45), s(21, 0))
        ),
        Movie(2, "Película B", MovieStatus.CARTELERA, sessions = listOf(s(17, 0), s(20, 15))),
        Movie(3, "Película C", MovieStatus.ESTRENO, sessions = listOf(s(19, 30, 1), s(22, 0, 1))),
        Movie(4, "Película D", MovieStatus.ESTRENO, sessions = emptyList()),
        Movie(
            5,
            "Película E",
            MovieStatus.CARTELERA,
            sessions = listOf(s(15, 45), s(18, 0), s(20, 30))
        ),
        Movie(6, "Película F", MovieStatus.CARTELERA, sessions = listOf(s(16, 0, 2)))
    )
}