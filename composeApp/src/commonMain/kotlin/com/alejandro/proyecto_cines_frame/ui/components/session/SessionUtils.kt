package com.alejandro.proyecto_cines_frame.ui.components.session

import com.alejandro.proyecto_cines_frame.domain.model.Session
import kotlinx.datetime.LocalDate
//Utilidades de sesiones

//Filtra sesiones por fecha concreta
fun sessionsForDate(
    sessions: List<Session>,
    date: LocalDate
): List<Session> {
    return sessions.filter {
        it.dateTime.date == date
    }
}