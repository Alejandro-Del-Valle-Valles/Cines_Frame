package com.alejandro.proyecto_cines_frame.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class HoldToken (
    val holdToken: String,
    val expira: LocalDateTime
) {
    fun isExpired(now: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())) = now > expira
}