package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.SessionCard
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.SessionUiState
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun ManageSessionsMobile(
    state: SessionUiState,
    onAddSession: () -> Unit,
    onDeleteSession: (Sesion) -> Unit
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
                        onDeleteSession = onDeleteSession
                    )
                }
            }
        }
    }
}