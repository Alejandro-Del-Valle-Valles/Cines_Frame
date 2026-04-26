package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.theme.*

@Composable
fun CheckoutHeaderTop(
    session: Sesion,
    remainingSeconds: Long,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(
                onClick = onBack,
                modifier = Modifier.size(40.dp)
            ) {
                Text("<", color = TextWhite)
            }

            Spacer(Modifier.width(16.dp))

            Text(
                session.pelicula.nombre,
                color = TextWhite,
                style = MaterialTheme.typography.headlineLarge
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = buildString {
                    append("Hoy ")
                    append("${session.horario.hour}:${session.horario.minute}")
                    if (session.tresD) append(" • 3D")
                    if (session.vose) append(" | VOSE")
                },
                color = TextGray
            )

            Text(
                text = "Tiempo restante: ${formatCountdown(remainingSeconds)}",
                color = if (remainingSeconds > 0) TextWhite else TextGray,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

private fun formatCountdown(seconds: Long): String {
    val safeSeconds = if (seconds < 0) 0 else seconds
    val minutes = safeSeconds / 60
    val remSeconds = safeSeconds % 60
    return "%02d:%02d".format(minutes, remSeconds)
}
