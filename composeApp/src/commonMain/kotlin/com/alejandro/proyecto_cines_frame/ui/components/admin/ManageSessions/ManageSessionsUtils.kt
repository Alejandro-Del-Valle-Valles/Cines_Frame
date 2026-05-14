package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

object ManageSessionsUtils {

    val PuntoCorteEscritorio: Dp = 800.dp

    fun esEscritorio(maxWidth: Dp): Boolean {
        return maxWidth >= PuntoCorteEscritorio
    }

    fun Sesion.toFormState(): SessionFormState {
        return SessionFormState(
            peliculaId = pelicula.id,
            peliculaNombre = pelicula.nombre,
            numSala = numSala.toString(),
            fecha = horario.date.toString(),
            hora = "%02d:%02d".format(horario.time.hour, horario.time.minute),
            tresD = tresD,
            vose = vose
        )
    }

    fun formatDate(dateTime: LocalDateTime): String {
        return dateTime.date.toString()
    }

    fun formatTime(dateTime: LocalDateTime): String {
        return "%02d:%02d".format(dateTime.time.hour, dateTime.time.minute)
    }

    fun formatDateTime(dateTime: LocalDateTime): String {
        return "${formatDate(dateTime)} ${formatTime(dateTime)}"
    }

    fun parseFormDateTime(fecha: String, hora: String): LocalDateTime? {
        val date = runCatching { LocalDate.parse(fecha.trim()) }.getOrNull() ?: return null
        val time = runCatching { LocalTime.parse(hora.trim()) }.getOrNull() ?: return null
        return LocalDateTime(date, time)
    }

    fun sampleSessions(): List<Sesion> {
        return listOf(
            Sesion(
                numSala = 1,
                pelicula = samplePelicula("bc891edf-062f-44ce-9811-bdda18e0687c", "Dune: Parte Dos"),
                horario = LocalDateTime(
                    date = LocalDate(2026, 5, 9),
                    time = LocalTime(17, 45)
                ),
                tresD = true,
                vose = true
            ),
            Sesion(
                numSala = 2,
                pelicula = samplePelicula("0b030685-18e3-4da5-a08e-fdc3fd10e733", "Godzilla y Kong"),
                horario = LocalDateTime(
                    date = LocalDate(2026, 5, 9),
                    time = LocalTime(20, 30)
                ),
                tresD = false,
                vose = true
            )
        )
    }

    private fun samplePelicula(id: String, nombre: String): Pelicula {
        return Pelicula(
            id = id,
            nombre = nombre,
            descripcion = "",
            estado = PeliculaEstado.CARTELERA,
            portada = null,
            duracion = LocalTime(1, 30),
            genero = PeliculaGenero.ACCION
        )
    }
}