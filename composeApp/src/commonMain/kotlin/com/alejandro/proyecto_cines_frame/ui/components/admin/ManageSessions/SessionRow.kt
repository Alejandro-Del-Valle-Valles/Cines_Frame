package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun SessionRow(
    session: Sesion,
    onDeleteSession: (Sesion) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = session.pelicula.nombre,
            modifier = Modifier.weight(2f),
            color = TextWhite,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "Sala ${session.numSala}",
            modifier = Modifier.weight(0.9f),
            color = TextWhite
        )

        Text(
            text = ManageSessionsUtils.formatDateTime(session.horario),
            modifier = Modifier.weight(1.2f),
            color = TextWhite
        )

        Box(
            modifier = Modifier.weight(0.7f)
        ) {
            if (session.tresD) SessionTag("3D") else Text("—", color = TextWhite)
        }

        Box(
            modifier = Modifier.weight(0.8f)
        ) {
            if (session.vose) SessionTag("VOSE") else Text("—", color = TextWhite)
        }

        Row(
            modifier = Modifier.weight(0.8f),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { onDeleteSession(session) }) {
                Text("🗑️")
            }
        }
    }

    HorizontalDivider()
}