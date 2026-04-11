package com.alejandro.proyecto_cines_frame.ui.components.grid

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max
//aqui se puede añadir mas utilidades, para reutilizar en grid, no me seais cutres porfi

data class CardSize(
    val width: Dp,
    val posterHeight: Dp,
    val scale: Float
)

//LA unica solucion al tema de la redimension, gracias a mi mommy Vasca por la idea
fun calculateCardSize(maxWidth: Dp): CardSize {
    return when {
        maxWidth < 520.dp -> CardSize(width = 110.dp, posterHeight = 160.dp, scale = 1f)
        maxWidth < 800.dp -> CardSize(width = 125.dp, posterHeight = 180.dp, scale = 1.08f)
        maxWidth < 1100.dp -> CardSize(width = 145.dp, posterHeight = 205.dp, scale = 1.16f)
        maxWidth < 1500.dp -> CardSize(width = 165.dp, posterHeight = 230.dp, scale = 1.24f)
        else -> CardSize(width = 180.dp, posterHeight = 250.dp, scale = 1.30f)
    }
}

// Calcula las columnas según el tamaño real de la card
fun calculateColumns(
    availableWidth: Dp,
    itemWidth: Dp,
    spacing: Dp = 12.dp
): Int {
    return max(1, ((availableWidth + spacing) / (itemWidth + spacing)).toInt())
}
