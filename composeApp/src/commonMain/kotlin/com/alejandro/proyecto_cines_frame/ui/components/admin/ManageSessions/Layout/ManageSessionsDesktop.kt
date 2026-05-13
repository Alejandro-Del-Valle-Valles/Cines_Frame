package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.SessionTable
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.SessionUiModel
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.SessionUiState
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun ManageSessionsDesktop(
    state: SessionUiState,
    onAddSession: () -> Unit,
    onEditSession: (SessionUiModel) -> Unit,
    onDeleteSession: (SessionUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 72.dp,
                bottom = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Gestionar sesiones",
                    color = TextWhite,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Administra las sesiones de las películas en las salas.",
                    color = TextWhite
                )
            }

            Button(
                onClick = onAddSession,
                colors = ButtonDefaults.buttonColors(containerColor = OtroRojo)
            ) {
                Text("+  Nueva sesión")
            }
        }

        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = ColorFondoHeader)
        ) {
            SessionTable(
                sessions = state.sessions,
                onEditSession = onEditSession,
                onDeleteSession = onDeleteSession
            )
        }
    }
}