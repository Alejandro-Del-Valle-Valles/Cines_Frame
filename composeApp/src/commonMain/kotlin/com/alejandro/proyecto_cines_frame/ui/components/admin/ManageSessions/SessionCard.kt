package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun SessionCard(
    session: SessionUiModel,
    onEditSession: (SessionUiModel) -> Unit,
    onDeleteSession: (SessionUiModel) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = session.peliculaNombre,
                color = TextWhite,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Sala ${session.numSala} · ${ManageSessionsUtils.formatDateTime(session.horario)}",
                color = TextWhite,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (session.tresD) SessionTag("3D") else SessionTag("3D", active = false)
                if (session.vose) SessionTag("VOSE") else SessionTag("VOSE", active = false)
            }

            Row {
                TextButton(onClick = { onEditSession(session) }) {
                    Text("✏️")
                }
                TextButton(onClick = { onDeleteSession(session) }) {
                    Text("🗑️")
                }
            }
        }
    }
}