package com.alejandro.proyecto_cines_frame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, RoundedCornerShape(8.dp))
        )

        Spacer(Modifier.width(10.dp))

        Text(
            "CINES FRAMES",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.weight(1f))

        TextButton(onClick = {}) { Text("Cartelera") }
        TextButton(onClick = {}) { Text("Cines") }
        TextButton(onClick = {}) { Text("Mi cuenta") }
    }
}