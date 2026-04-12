package com.alejandro.proyecto_cines_frame.ui.components.session

import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

//Utilidades de sesiones

//Filtra sesiones por fecha concreta
fun sessionsForDate(
    sesions: List<Sesion>,
    date: LocalDate
): List<Sesion> {
    return sesions.filter {
        it.horario.date == date
    }
}

fun formatTime(session: Sesion): String {
    return "%02d:%02d".format(
        session.horario.hour,
        session.horario.minute
    )
}

fun formatDate(dateTime: LocalDateTime): String {
    val month = when (dateTime.month.number) {
        1 -> "Ene"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Abr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Ago"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dic"
        else -> ""
    }

    return "${dateTime.day} $month"
}
