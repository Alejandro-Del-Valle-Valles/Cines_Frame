package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EntradaDTO(
    val sesion: SesionDTO,
    val numFila: Int,
    val numButaca: Int,
    val tipo: TipoEntradaDTO
)
