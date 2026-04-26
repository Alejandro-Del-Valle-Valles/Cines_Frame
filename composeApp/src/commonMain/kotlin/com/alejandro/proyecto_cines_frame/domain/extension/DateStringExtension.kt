package com.alejandro.proyecto_cines_frame.domain.extension

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

fun String.toLocalDateTime() : LocalDateTime = LocalDateTime.Companion.parse(this)
fun String.toLocalDate() : LocalDate = LocalDate.Companion.parse(this)
fun String.toLocalTime() : LocalTime = LocalTime.Companion.parse(this)
