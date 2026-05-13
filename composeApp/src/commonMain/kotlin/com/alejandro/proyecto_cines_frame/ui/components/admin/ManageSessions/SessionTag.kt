package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray

@Composable
fun SessionTag(
    text: String,
    active: Boolean = true
) {
    val bg = if (active) Color(0xFF22C55E).copy(alpha = 0.15f) else TextGray.copy(alpha = 0.15f)
    val fg = if (active) Color(0xFF22C55E) else TextGray

    Box(
        modifier = Modifier
            .background(bg, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = fg)
    }
}