package com.alejandro.proyecto_cines_frame.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.*
// Texto alternativo para activar o desactivar filtros
@Composable
fun ToggleText(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = if (selected) TextWhite else TextGray,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
        modifier = Modifier
            .shadow(if (selected) 6.dp else 0.dp)
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 2.dp)
    )
}