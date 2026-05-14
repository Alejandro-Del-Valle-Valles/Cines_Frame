package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

import com.alejandro.proyecto_cines_frame.domain.model.Sesion

data class SessionUiState(
    val sessions: List<Sesion> = emptyList(),
    val isDialogVisible: Boolean = false,
    val editingSession: Sesion? = null,
    val form: SessionFormState = SessionFormState(),
    val errors: SessionFormErrors = SessionFormErrors()
)