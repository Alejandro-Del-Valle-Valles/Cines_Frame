package com.alejandro.proyecto_cines_frame.ui.components.features.movies

import com.alejandro.proyecto_cines_frame.domain.model.Movie
import com.alejandro.proyecto_cines_frame.domain.model.MovieGenre
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
    private val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    private fun s(
        hour: Int,
        minute: Int,
        daysFromToday: Int = 0,
        is3D: Boolean = false,
        isVOSE: Boolean = false
    ): Session {
        val date = now.date.plus(daysFromToday, DateTimeUnit.DAY)
        return Session(
            dateTime = LocalDateTime(date.year, date.month, date.dayOfMonth, hour, minute),
            is3D = is3D,
            isVOSE = isVOSE
        )
    }

    fun getMovies(): List<Movie> = listOf(
        Movie(
            id = 1,
            title = "Película A",
            status = MovieStatus.CARTELERA,
            genres = listOf(MovieGenre.AVENTURA, MovieGenre.COMEDIA),
            sessions = listOf(
                s(16, 30, is3D = false, isVOSE = false),
                s(18, 45, is3D = true, isVOSE = false),
                s(21, 0, is3D = false, isVOSE = true)
            )
        ),
        Movie(
            id = 2,
            title = "Película B",
            status = MovieStatus.CARTELERA,
            genres = listOf(MovieGenre.ACCION, MovieGenre.THRILLER),
            sessions = listOf(
                s(17, 0, is3D = false, isVOSE = false),
                s(20, 15, is3D = true, isVOSE = true)
            )
        ),
        Movie(
            id = 3,
            title = "Película C",
            status = MovieStatus.ESTRENO,
            genres = listOf(MovieGenre.FANTASIA, MovieGenre.ANIMACION),
            sessions = listOf(
                s(19, 30, daysFromToday = 1, is3D = true, isVOSE = false),
                s(22, 0, daysFromToday = 1, is3D = false, isVOSE = true)
            )
        ),
        Movie(
            id = 4,
            title = "Película D",
            status = MovieStatus.ESTRENO,
            genres = listOf(MovieGenre.DRAMA),
            sessions = emptyList()
        ),
        Movie(
            id = 5,
            title = "Película E",
            status = MovieStatus.CARTELERA,
            genres = listOf(MovieGenre.COMEDIA, MovieGenre.FAMILIA),
            sessions = listOf(
                s(15, 45, is3D = false, isVOSE = false),
                s(18, 0, is3D = true, isVOSE = true),
                s(20, 30, is3D = false, isVOSE = true)
            )
        ),
        Movie(
            id = 6,
            title = "Película F",
            status = MovieStatus.CARTELERA,
            genres = listOf(MovieGenre.TERROR),
            sessions = listOf(
                s(16, 0, daysFromToday = 2, is3D = true, isVOSE = false)
            )
        )
    )
}