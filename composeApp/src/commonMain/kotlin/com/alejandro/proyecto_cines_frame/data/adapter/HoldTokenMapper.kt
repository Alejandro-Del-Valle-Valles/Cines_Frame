package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldTokenResponse
import com.alejandro.proyecto_cines_frame.domain.model.HoldToken
import com.alejandro.proyecto_cines_frame.domain.extension.toLocalDateTime

fun HoldTokenResponse.toDomain() : HoldToken = HoldToken(
    holdToken = holdToken,
    expira = expira.toLocalDateTime()
)
