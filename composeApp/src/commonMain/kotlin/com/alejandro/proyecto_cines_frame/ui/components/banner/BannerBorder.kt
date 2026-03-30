package com.alejandro.proyecto_cines_frame.ui.components.banner

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color

@Composable
fun BannerBorder(
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {

        val config = calculateBannerBorderConfig(maxWidth)

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(config.borderHeight)
        ) {

            val holeWidth = config.holeWidth.toPx()
            val holeHeight = config.holeHeight.toPx()
            val gap = config.gap.toPx()
            val radius = config.cornerRadius.toPx()

            val step = holeWidth + gap
            drawRect(Color.Black)
            val top = (size.height - holeHeight) / 2f

            val startX = -step / 2f
            var x = startX
            while (x < size.width + step) {
                drawRoundRect(
                    color = Color.White,
                    topLeft = Offset(x, top),
                    size = Size(holeWidth, holeHeight),
                    cornerRadius = CornerRadius(radius, radius)
                )
                x += step
            }
        }
    }
}