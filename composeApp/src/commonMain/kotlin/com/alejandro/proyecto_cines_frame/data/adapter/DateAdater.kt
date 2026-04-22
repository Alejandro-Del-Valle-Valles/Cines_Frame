package com.alejandro.proyecto_cines_frame.data.adapter

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

object DateAdater {

    fun toLocalDateTime(date: String) : LocalDateTime = LocalDateTime.parse(date)
    fun toLocalDate(date: String) : LocalDate = LocalDate.parse(date)
    fun toLocalTime(time: String) : LocalTime = LocalTime.parse(time)
}