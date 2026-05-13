package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.SessionCard
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.SessionUiModel
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.SessionUiState
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun ManageSessionsMobile(
    state: SessionUiState,
    onAddSession: () -> Unit,
    onEditSession: (SessionUiModel) -> Unit,
    onDeleteSession: (SessionUiModel) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddSession,
                containerColor = OtroRojo,
                contentColor = TextWhite
            ) {
                Text("+")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(padding)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 90.dp,
                    bottom = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Gestionar sesiones",
                color = TextWhite,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Administra las sesiones de las películas.",
                color = TextWhite,
                style = MaterialTheme.typography.bodyMedium
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.sessions) { session ->
                    SessionCard(
                        session = session,
                        onEditSession = onEditSession,
                        onDeleteSession = onDeleteSession
                    )
                }
            }
        }
    }
}