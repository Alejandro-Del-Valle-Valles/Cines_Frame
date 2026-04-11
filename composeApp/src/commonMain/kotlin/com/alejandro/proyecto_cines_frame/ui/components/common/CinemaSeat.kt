package com.alejandro.proyecto_cines_frame.ui.components.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.alejandro.proyecto_cines_frame.ui.theme.ColorAsientoDisponible
import com.alejandro.proyecto_cines_frame.ui.theme.ColorAsientoSeleccionado
import com.alejandro.proyecto_cines_frame.ui.theme.ColorAsientoOcupado

@Composable
fun CinemaSeat(
    isSelected: Boolean,
    isReserved: Boolean = false,
    onClick: () -> Unit
) {
    val color = when {
        isReserved -> ColorAsientoOcupado
        isSelected -> ColorAsientoSeleccionado
        else -> ColorAsientoDisponible
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clickable(enabled = !isReserved) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            val seatWidth = w * 0.6f
            val seatHeight = h * 0.6f

            val left = (w - seatWidth) / 2
            val top = (h - seatHeight) / 2

            // Asiento principal (cuadrado)
            drawRoundRect(
                color = color,
                topLeft = Offset(left, top),
                size = Size(seatWidth, seatHeight),
                cornerRadius = CornerRadius(6f, 6f)
            )

            val armWidth = w * 0.15f

            // Brazo izquierdo
            drawRoundRect(
                color = color,
                topLeft = Offset(left - armWidth, top + h * 0.1f),
                size = Size(armWidth, seatHeight * 0.8f),
                cornerRadius = CornerRadius(6f, 6f)
            )

            // Brazo derecho
            drawRoundRect(
                color = color,
                topLeft = Offset(left + seatWidth, top + h * 0.1f),
                size = Size(armWidth, seatHeight * 0.8f),
                cornerRadius = CornerRadius(6f, 6f)
            )
        }
    }
}

@Preview
@Composable
fun PreviewCinemaSeat() {
    CinemaSeat(
        isSelected = false,
        isReserved = false,
        onClick = {}
    )
}
