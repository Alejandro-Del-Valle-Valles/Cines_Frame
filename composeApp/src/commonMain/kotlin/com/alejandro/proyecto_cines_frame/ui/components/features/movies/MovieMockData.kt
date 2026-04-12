package com.alejandro.proyecto_cines_frame.ui.components.features.movies

import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.model.Sala
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import kotlinx.datetime.*
import kotlin.time.Clock

object MovieMockData {

    private val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    private val today = now.date
    private val tomorrow = today.plus(1, DateTimeUnit.DAY)
    private val dayAfter = today.plus(2, DateTimeUnit.DAY)

    // 🔹 GENERADOR DE PELÍCULAS
    private fun createMovie(id: String, name: String, estado: PeliculaEstado): Pelicula {
        return Pelicula(
            id = id,
            nombre = name,
            descripcion = "Mock",
            estado = estado,
            portada = null,
            duracion = LocalTime(2, 0),
            genero = PeliculaGenero.ACCION
        )
    }

    fun getPeliculas(): List<Pelicula> {
        return buildList {

            // 🎬 HOY (muchas)
            repeat(8) {
                add(createMovie("h$it", "Peli Hoy $it", PeliculaEstado.CARTELERA))
            }

            // 🎬 MAÑANA
            repeat(8) {
                add(createMovie("m$it", "Peli Mañana $it", PeliculaEstado.CARTELERA))
            }

            // 🎬 PASADO
            repeat(8) {
                add(createMovie("p$it", "Peli Futuro $it", PeliculaEstado.CARTELERA))
            }

            // 🎬 ESTRENOS
            repeat(5) {
                add(createMovie("e$it", "Estreno $it", PeliculaEstado.ESTRENO))
            }
        }
    }

    fun getSesiones(): List<Sesion> {

        val peliculas = getPeliculas().associateBy { it.id }

        val sala1 = Sala(1, 100)
        val sala2 = Sala(2, 80)
        val sala3 = Sala(3, 120)

        return buildList {

            // 🔴 HOY
            (0 until 8).forEach { i ->
                val movie = peliculas["h$i"]!!
                add(Sesion(sala1, movie, LocalDateTime(today.year, today.month, today.day, 10 + i, 0), tresD = i % 2 == 0, vose = i % 3 == 0))
                add(Sesion(sala2, movie, LocalDateTime(today.year, today.month, today.day, 14 + i, 30), tresD = i % 2 != 0, vose = i % 2 == 0))
            }

            // 🟡 MAÑANA
            (0 until 8).forEach { i ->
                val movie = peliculas["m$i"]!!
                add(Sesion(sala1, movie, LocalDateTime(tomorrow.year, tomorrow.month, tomorrow.day, 10 + i, 0), tresD = i % 2 == 0, vose = false))
                add(Sesion(sala3, movie, LocalDateTime(tomorrow.year, tomorrow.month, tomorrow.day, 18, i * 5), tresD = false, vose = i % 2 == 0))
            }

            // 🔵 PASADO MAÑANA
            (0 until 8).forEach { i ->
                val movie = peliculas["p$i"]!!
                add(Sesion(sala2, movie, LocalDateTime(dayAfter.year, dayAfter.month, dayAfter.day, 12, i * 5), tresD = true, vose = i % 2 == 0))
            }

            // 🟣 ESTRENOS (siempre futuro)
            (0 until 5).forEach { i ->
                val movie = peliculas["e$i"]!!
                val estrenoDate = dayAfter.plus(i + 1, DateTimeUnit.DAY)

                add(
                    Sesion(
                        sala1,
                        movie,
                        LocalDateTime(estrenoDate.year, estrenoDate.month, estrenoDate.day, 20, 0),
                        tresD = true,
                        vose = false
                    )
                )
            }
        }
    }
}
