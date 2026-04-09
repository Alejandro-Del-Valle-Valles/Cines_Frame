package com.alejandro.proyecto_cines_frame.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.*
//Chip visual para marcar un día
@Composable
fun DayChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)

    Text(
        text = text,
        color = if (selected) TextWhite else TextGray,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
        modifier = Modifier
            .shadow(if (selected) 8.dp else 0.dp, shape)
            .border(
                width = if (selected) 1.dp else 0.dp,
                color = if (selected) PrimaryRed else Color.Transparent,
                shape = shape
            )
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
    )
}