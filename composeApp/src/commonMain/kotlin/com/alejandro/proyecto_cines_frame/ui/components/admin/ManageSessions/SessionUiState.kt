package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

data class SessionUiState(
    val sessions: List<SessionUiModel> = emptyList(),
    val isDialogVisible: Boolean = false,
    val editingSession: SessionUiModel? = null,
    val form: SessionFormState = SessionFormState(),
    val errors: SessionFormErrors = SessionFormErrors()
)