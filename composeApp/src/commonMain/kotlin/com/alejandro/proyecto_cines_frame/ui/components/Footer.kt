package com.alejandro.proyecto_cines_frame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Footer() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text("CINES FRAMES", color = Color.White)

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Ayuda", color = Color.Gray)
            Text("Privacidad", color = Color.Gray)
            Text("Contacto", color = Color.Gray)
        }
    }
}