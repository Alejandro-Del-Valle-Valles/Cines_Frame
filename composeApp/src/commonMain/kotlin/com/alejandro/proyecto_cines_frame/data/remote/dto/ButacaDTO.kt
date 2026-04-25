package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ButacaDTO (
    val fila: Int,
    val butaca: Int
) {

}