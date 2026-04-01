package com.alejandro.proyecto_cines_frame.ui.components.header

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HeaderActionButton(
    texto: String,
    alClick: () -> Unit
) {
    TextButton(
        onClick = alClick,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(
            text = texto,
            color = Color.White
        )
    }
}