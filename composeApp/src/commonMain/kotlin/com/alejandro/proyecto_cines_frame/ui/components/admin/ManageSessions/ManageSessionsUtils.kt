package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

object ManageSessionsUtils {

    val PuntoCorteEscritorio: Dp = 800.dp

    fun esEscritorio(maxWidth: Dp): Boolean {
        return maxWidth >= PuntoCorteEscritorio
    }

    fun SessionUiModel.toFormState(): SessionFormState {
        return SessionFormState(
            peliculaId = peliculaId,
            peliculaNombre = peliculaNombre,
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

    fun sampleSessions(): List<SessionUiModel> {
        return listOf(
            SessionUiModel(
                peliculaId = "bc891edf-062f-44ce-9811-bdda18e0687c",
                peliculaNombre = "Dune: Parte Dos",
                numSala = 1,
                horario = LocalDateTime(
                    date = LocalDate(2026, 5, 9),
                    time = LocalTime(17, 45)
                ),
                tresD = true,
                vose = true
            ),
            SessionUiModel(
                peliculaId = "0b030685-18e3-4da5-a08e-fdc3fd10e733",
                peliculaNombre = "Godzilla y Kong",
                numSala = 2,
                horario = LocalDateTime(
                    date = LocalDate(2026, 5, 9),
                    time = LocalTime(20, 30)
                ),
                tresD = false,
                vose = true
            ),
            SessionUiModel(
                peliculaId = "b4bbbeee-4cc1-4ba6-b44c-cdbb5d37ffe8",
                peliculaNombre = "Kung Fu Panda 4",
                numSala = 3,
                horario = LocalDateTime(
                    date = LocalDate(2026, 5, 12),
                    time = LocalTime(15, 30)
                ),
                tresD = true,
                vose = false
            ),
            SessionUiModel(
                peliculaId = "000fe352-0b03-4a6b-bd21-e410a1fcea2e",
                peliculaNombre = "Bad Boys 4",
                numSala = 4,
                horario = LocalDateTime(
                    date = LocalDate(2026, 5, 12),
                    time = LocalTime(18, 0)
                ),
                tresD = false,
                vose = false
            )
        )
    }
}