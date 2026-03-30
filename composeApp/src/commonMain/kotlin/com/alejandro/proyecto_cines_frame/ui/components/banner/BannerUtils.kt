package com.alejandro.proyecto_cines_frame.ui.components.banner

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import kotlin.math.max

data class BannerBorderConfig(
    val holeWidth: Dp,
    val holeHeight: Dp,
    val gap: Dp,
    val spacing: Dp,
    val borderHeight: Dp,
    val cornerRadius: Dp
)

object BannerDefaults {
    val BorderHeight = 44.dp
}
fun calculateBannerBorderConfig(maxWidth: Dp): BannerBorderConfig {

    val scale = when {
        maxWidth < 360.dp -> 0.9f
        maxWidth < 600.dp -> 1.0f
        maxWidth < 900.dp -> 1.1f
        else -> 1.2f
    }
    val holeWidth = (10.dp * scale)
    val holeHeight = (18.dp * scale)

    val gap = (8.dp * scale)

    val borderHeight = max(holeHeight + 12.dp, BannerDefaults.BorderHeight)

    val cornerRadius = (3.dp * scale)

    return BannerBorderConfig(
        holeWidth = holeWidth,
        holeHeight = holeHeight,
        gap = gap,
        spacing = gap,
        borderHeight = borderHeight,
        cornerRadius = cornerRadius
    )
}

fun getNextIndex(currentIndex: Int, size: Int): Int {
    if (size == 0) return 0
    return (currentIndex + 1) % size
}