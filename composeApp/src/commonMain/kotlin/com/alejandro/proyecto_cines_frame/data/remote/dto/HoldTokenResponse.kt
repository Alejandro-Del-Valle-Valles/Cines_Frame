package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HoldTokenResponse(
    val holdToken: String,
    val expira: String
)
