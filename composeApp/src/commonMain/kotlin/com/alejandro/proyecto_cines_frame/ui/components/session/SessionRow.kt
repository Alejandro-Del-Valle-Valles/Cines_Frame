package com.alejandro.proyecto_cines_frame.ui.components.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Session

@Composable
fun SessionRow(
    sessions: List<Session>,
    scale: Float = 1f
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp * scale),
        verticalArrangement = Arrangement.spacedBy(4.dp * scale)
    ) {
        sessions.forEach { session ->
            SessionChip(
                session = session,
                scale = scale
            )
        }
    }
}