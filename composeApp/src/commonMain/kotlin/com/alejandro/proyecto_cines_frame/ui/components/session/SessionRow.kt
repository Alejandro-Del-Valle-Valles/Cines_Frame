package com.alejandro.proyecto_cines_frame.ui.components.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
//Lista de sesiones en forma de fila flexible
@Composable
fun SessionRow(
    sesions: List<Sesion>,
    scale: Float = 1f
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp * scale),
        verticalArrangement = Arrangement.spacedBy(4.dp * scale)
    ) {
        sesions.forEach { session ->
            SessionChip(
                sesion = session,
                scale = scale
            )
        }
    }
}