package com.alejandro.proyecto_cines_frame.ui.components.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.SeatBlue
import com.alejandro.proyecto_cines_frame.ui.theme.SeatSelectedBlue
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun CinemaSeat(
    isSelected: Boolean,
    isReserved: Boolean = false,
    seatSize: Dp = 40.dp,
    contentDescription: String = "Butaca",
    onClick: () -> Unit
) {
    val fillColor = when {
        isSelected -> SeatSelectedBlue
        isReserved -> SeatBlue
        else -> TextGray
    }

    val stateText = when {
        isReserved -> "ocupada"
        isSelected -> "seleccionada"
        else -> "libre"
    }

    Box(
        modifier = Modifier
            .size(seatSize)
            .semantics {
                role = Role.Button
                this.contentDescription = "$contentDescription, $stateText"
                stateDescription = stateText
            }
            .clickable(enabled = !isReserved) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(seatSize)) {
            val w = size.width
            val h = size.height
            val seatWidth = w * 0.6f
            val seatHeight = h * 0.6f
            val left = (w - seatWidth) / 2
            val top = (h - seatHeight) / 2

            drawRoundRect(
                color = fillColor,
                topLeft = Offset(left, top),
                size = Size(seatWidth, seatHeight),
                cornerRadius = CornerRadius(6f, 6f)
            )
            val armWidth = w * 0.15f
            drawRoundRect(
                color = fillColor,
                topLeft = Offset(left - armWidth, top + h * 0.1f),
                size = Size(armWidth, seatHeight * 0.8f),
                cornerRadius = CornerRadius(6f, 6f)
            )
            drawRoundRect(
                color = fillColor,
                topLeft = Offset(left + seatWidth, top + h * 0.1f),
                size = Size(armWidth, seatHeight * 0.8f),
                cornerRadius = CornerRadius(6f, 6f)
            )
            if (isSelected) {
                drawRoundRect(
                    color = TextWhite,
                    topLeft = Offset(left - 2f, top - 2f),
                    size = Size(seatWidth + 4f, seatHeight + 4f),
                    cornerRadius = CornerRadius(7f, 7f),
                    style = Stroke(width = 2f)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCinemaSeatFree() {
    CinemaSeat(
        isSelected = false,
        isReserved = false,
        onClick = {}
    )
}