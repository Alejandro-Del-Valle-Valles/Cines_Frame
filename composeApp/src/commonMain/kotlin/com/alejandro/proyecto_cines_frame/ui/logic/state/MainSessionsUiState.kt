package com.alejandro.proyecto_cines_frame.ui.logic.state

import com.alejandro.proyecto_cines_frame.domain.model.Sesion

data class MainSessionsUiState(
    val sessions: List<Sesion> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)