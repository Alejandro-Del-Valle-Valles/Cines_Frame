package com.alejandro.proyecto_cines_frame.ui.components.filter

import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import kotlinx.datetime.*
import kotlin.time.Clock
//Utils normaluchos para filtros
// Genera los días visibles de cartelera
fun buildCarteleraDays(): List<FilterDay> {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    return (0..2).map { offset ->
        val date = today.plus(offset, DateTimeUnit.DAY)

        FilterDay(
            date = date,
            label = if (offset == 0) "Hoy"
            else "${weekdayShort(date)} ${date.day}"
        )
    }
}

fun buildCalendarDays(): List<FilterDay> {
    val today = kotlin.time.Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    return (0..6).map { offset ->
        val date = today.plus(offset, DateTimeUnit.DAY)

        FilterDay(
            date = date,
            label = if (offset == 0) "Hoy"
            else "${weekdayShort(date)} ${date.day}"
        )
    }
}
//Sin esto el usuario ve 1, 2, 3 XD
private fun weekdayShort(date: LocalDate): String {
    return when (date.dayOfWeek) {
        DayOfWeek.MONDAY -> "Lun"
        DayOfWeek.TUESDAY -> "Mar"
        DayOfWeek.WEDNESDAY -> "Mié"
        DayOfWeek.THURSDAY -> "Jue"
        DayOfWeek.FRIDAY -> "Vie"
        DayOfWeek.SATURDAY -> "Sáb"
        DayOfWeek.SUNDAY -> "Dom"
    }
}
/*
fun List<Pelicula>.applyFilters(state: FilterState): List<Pelicula> {
    return mapNotNull { movie ->


        val sessions = movie.sesiones.filter { s ->

            val matchDay = state.selectedDay?.let {
                s.horario.date == it.date
            } ?: true

            val match3D = state.is3D?.let {
                s.is3D == it
            } ?: true

            val matchVOSE = if (state.isVOSE) s.isVOSE else true

            matchDay && match3D && matchVOSE
        }

        if (sessions.isEmpty()) null else movie.copy(sesiones = sessions)
    }
}*/