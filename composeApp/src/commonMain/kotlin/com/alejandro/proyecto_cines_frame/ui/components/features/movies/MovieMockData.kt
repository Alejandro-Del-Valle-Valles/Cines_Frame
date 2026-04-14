package com.alejandro.proyecto_cines_frame.ui.components.features.movies

import com.alejandro.proyecto_cines_frame.data.remote.api.KtorPeliculaApi
import com.alejandro.proyecto_cines_frame.data.remote.client.HttpClientFactory
import com.alejandro.proyecto_cines_frame.data.repository.PeliculaRepositoryImpl
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.model.Sala
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

object MovieMockData {

    private val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    private val today = now.date
    private val tomorrow = today.plus(1, DateTimeUnit.DAY)
    private val dayAfter = today.plus(2, DateTimeUnit.DAY)
    private val client = HttpClientFactory.create()
    private val peliculaApi = KtorPeliculaApi(client)
    private val repo = PeliculaRepositoryImpl(peliculaApi)

    fun getPeliculas(): List<Pelicula> = runBlocking {
        when (val result = repo.getAllBasic()) {
            is ApiResult.Success -> result.data
            is ApiResult.Error -> emptyList() // Fallback to an empty list on error
        }
    }

    /**
     * Genera sesiones "en memoria" usando películas reales obtenidas de la API.
     * No depende de IDs ficticios ("h0", "m0"...).
     */
    fun getSesiones(): List<Sesion> {
        val peliculas = getPeliculas()

        if (peliculas.isEmpty()) return emptyList()

        val sala1 = Sala(1, 100)
        val sala2 = Sala(2, 80)
        val sala3 = Sala(3, 120)

        fun movieAt(index: Int): Pelicula = peliculas[index % peliculas.size]

        return buildList {
            // 🔴 HOY (16 sesiones: 2 por iteración x 8)
            (0 until 8).forEach { i ->
                val movie = movieAt(i)
                add(
                    Sesion(
                        sala1.numero,
                        movie,
                        LocalDateTime(today.year, today.month, today.day, 10 + i, 0),
                        tresD = i % 2 == 0,
                        vose = i % 3 == 0
                    )
                )
                add(
                    Sesion(
                        sala2.numero,
                        movie,
                        LocalDateTime(today.year, today.month, today.day, 14 + i, 30),
                        tresD = i % 2 != 0,
                        vose = i % 2 == 0
                    )
                )
            }

            // 🟡 MAÑANA (16 sesiones)
            (0 until 8).forEach { i ->
                val movie = movieAt(8 + i)
                add(
                    Sesion(
                        sala1.numero,
                        movie,
                        LocalDateTime(tomorrow.year, tomorrow.month, tomorrow.day, 10 + i, 0),
                        tresD = i % 2 == 0,
                        vose = false
                    )
                )
                add(
                    Sesion(
                        sala3.numero,
                        movie,
                        LocalDateTime(tomorrow.year, tomorrow.month, tomorrow.day, 18, i * 5),
                        tresD = false,
                        vose = i % 2 == 0
                    )
                )
            }

            // 🔵 PASADO MAÑANA (8 sesiones)
            (0 until 8).forEach { i ->
                val movie = movieAt(16 + i)
                add(
                    Sesion(
                        sala2.numero,
                        movie,
                        LocalDateTime(dayAfter.year, dayAfter.month, dayAfter.day, 12, i * 5),
                        tresD = true,
                        vose = i % 2 == 0
                    )
                )
            }

            // 🟣 ESTRENOS (5 sesiones, siempre futuro)
            (0 until 5).forEach { i ->
                val movie = movieAt(24 + i)
                val estrenoDate = dayAfter.plus(i + 1, DateTimeUnit.DAY)

                add(
                    Sesion(
                        sala1.numero,
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