package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun SessionTable(
    sessions: List<Sesion>,
    onDeleteSession: (Sesion) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Película", modifier = Modifier.weight(2f), color = TextWhite)
            Text("Sala", modifier = Modifier.weight(0.9f), color = TextWhite)
            Text("Horario", modifier = Modifier.weight(1.2f), color = TextWhite)
            Text("3D", modifier = Modifier.weight(0.7f), color = TextWhite)
            Text("VOSE", modifier = Modifier.weight(0.8f), color = TextWhite)
            Text("Acciones", modifier = Modifier.weight(0.8f), color = TextWhite)
        }

        Spacer(Modifier.height(12.dp))
        HorizontalDivider()
        Spacer(Modifier.height(4.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(sessions) { session ->
                SessionRow(
                    session = session,
                    onDeleteSession = onDeleteSession
                )
            }
        }
    }
}