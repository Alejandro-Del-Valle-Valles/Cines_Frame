package com.alejandro.proyecto_cines_frame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MovieCard() {
    Column {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
                .background(Color.Gray, RoundedCornerShape(10.dp))
        )

        Spacer(Modifier.height(6.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(24.dp, 18.dp)
                        .background(Color.DarkGray, RoundedCornerShape(4.dp))
                )
            }
        }
    }
}