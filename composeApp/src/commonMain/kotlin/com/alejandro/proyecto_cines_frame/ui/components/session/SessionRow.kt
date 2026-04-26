package com.alejandro.proyecto_cines_frame.ui.components.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.model.Sesion

@Composable
fun SessionRow(
    sessions: List<Sesion>,
    scale: Float,
    onSessionClick: (Sesion) -> Unit = {},
    formatTime: (Sesion) -> String = {
        "%02d:%02d".format(it.horario.hour, it.horario.minute)
    }
) {
    if (sessions.isEmpty()) return

    val isEstreno = sessions.first().pelicula.estado == PeliculaEstado.ESTRENO

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy((6 * scale).dp),
        verticalArrangement = Arrangement.spacedBy((6 * scale).dp)
    ) {

        if (isEstreno) {
            val firstSession = sessions.minBy { it.horario }

            SessionChip(
                session = firstSession,
                text = formatDate(firstSession.horario),
                scale = scale,
                showIcons = false,
                fillWidth = true,
                onClick = onSessionClick
            )

        } else {
            sessions.forEach { session ->
                SessionChip(
                    session = session,
                    text = formatTime(session),
                    scale = scale,
                    showIcons = true,
                    fillWidth = false,
                    onClick = onSessionClick
                )
            }
        }
    }
}