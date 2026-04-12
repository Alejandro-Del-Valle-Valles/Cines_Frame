package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.theme.*

@Composable
fun CheckoutHeaderTop(
    session: Sesion,
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

        Text(
            text = buildString {
                append("Hoy ")
                append("${session.horario.hour}:${session.horario.minute}")
                if (session.tresD) append(" • 3D")
                if (session.vose) append(" | VOSE")
            },
            color = TextGray
        )
    }
}