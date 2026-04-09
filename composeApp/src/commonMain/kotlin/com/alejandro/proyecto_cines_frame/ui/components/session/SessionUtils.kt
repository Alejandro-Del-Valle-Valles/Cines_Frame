package com.alejandro.proyecto_cines_frame.ui.components.session

import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import kotlinx.datetime.LocalDate
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