package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alejandro.proyecto_cines_frame.ui.theme.*

@Composable
fun SeatLegendCentered() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .background(SurfaceDark)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        LegendItem("Disponible", TextGray)
        LegendItem("Seleccionado", SeatSelectedBlue)
        LegendItem("Ocupado", SeatBlue)
    }
}

@Composable
private fun LegendItem(text: String, color: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(14.dp).background(color))
        Spacer(Modifier.width(6.dp))
        Text(text, color = TextGray)
    }
}