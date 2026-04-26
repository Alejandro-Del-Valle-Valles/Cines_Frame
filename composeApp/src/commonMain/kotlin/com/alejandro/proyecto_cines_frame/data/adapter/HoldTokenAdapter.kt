package com.alejandro.proyecto_cines_frame.data.adapter

import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldTokenResponse
import com.alejandro.proyecto_cines_frame.domain.model.HoldToken
import com.alejandro.proyecto_cines_frame.domain.extension.toLocalDateTime

object HoldTokenAdapter {

    fun toHoldToken(holdTokenResponse: HoldTokenResponse) : HoldToken = HoldToken(
        holdToken = holdTokenResponse.holdToken,
        expira = holdTokenResponse.expira.toLocalDateTime()
    )
}