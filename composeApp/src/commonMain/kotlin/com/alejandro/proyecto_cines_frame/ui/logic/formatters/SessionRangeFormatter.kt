package com.alejandro.proyecto_cines_frame.ui.logic.formatters

import com.alejandro.proyecto_cines_frame.ui.components.filter.FilterDay
import kotlinx.datetime.LocalDateTime

object SessionRangeFormatter {

    /**
     * Construye un rango inclusivo [inicio, fin] para los dias visibles del calendario.
     * Se serializa con toString() para mantener el formato ISO por defecto tipo Java LocalDateTime.
     */
    fun buildRangeForCalendarDays(calendarDays: List<FilterDay>): Pair<String, String>? {
        val firstDay = calendarDays.minByOrNull { it.date }
        val lastDay = calendarDays.maxByOrNull { it.date }

        if (firstDay == null || lastDay == null) return null

        val start = LocalDateTime(
            year = firstDay.date.year,
            month = firstDay.date.month,
            day = firstDay.date.day,
            hour = 0,
            minute = 0,
            second = 0,
            nanosecond = 0
        )
        val end = LocalDateTime(
            year = lastDay.date.year,
            month = lastDay.date.month,
            day = lastDay.date.day,
            hour = 23,
            minute = 59,
            second = 59,
            nanosecond = 0
        )

        return start.toString() to end.toString()
    }
}