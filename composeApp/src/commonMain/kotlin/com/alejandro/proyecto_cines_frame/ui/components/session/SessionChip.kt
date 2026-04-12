package com.alejandro.proyecto_cines_frame.ui.components.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Sesion

//Chip individual de sesión con la hora, vamos donde esta la hora de las pelis
@Composable
fun SessionChip(
    text: String,
    scale: Float = 1f
) {
    val textStyle = if (scale > 1.1f) {
        MaterialTheme.typography.labelMedium
    } else {
        MaterialTheme.typography.labelSmall
    }

    Box(
        modifier = Modifier
            .background(Color.Black, RoundedCornerShape(4.dp * scale))
            .padding(
                horizontal = 6.dp * scale,
                vertical = 2.dp * scale
            )
    ) {
        Text(
            text = text,
            color = Color.White,
            style = textStyle
        )
    }
}
