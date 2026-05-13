package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.Layout.ManageSessionsDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.Layout.ManageSessionsMobile

@Composable
fun ManageSessions(
    state: SessionUiState,
    onAddSession: () -> Unit,
    onEditSession: (SessionUiModel) -> Unit,
    onDeleteSession: (SessionUiModel) -> Unit
) {
    BoxWithConstraints {
        if (ManageSessionsUtils.esEscritorio(maxWidth)) {
            ManageSessionsDesktop(
                state = state,
                onAddSession = onAddSession,
                onEditSession = onEditSession,
                onDeleteSession = onDeleteSession
            )
        } else {
            ManageSessionsMobile(
                state = state,
                onAddSession = onAddSession,
                onEditSession = onEditSession,
                onDeleteSession = onDeleteSession
            )
        }
    }
}