package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner

data class BannerUiModel(
    val peliculaId: String,
    val titulo: String,
    val imageUrl: String,
    val fechaInicio: String,
    val fechaFin: String,
    val activo: Boolean
)