package com.alejandro.proyecto_cines_frame.ui.logic.state

import com.alejandro.proyecto_cines_frame.domain.model.Baner

data class BannersUiState(
    val banners: List<Baner> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
