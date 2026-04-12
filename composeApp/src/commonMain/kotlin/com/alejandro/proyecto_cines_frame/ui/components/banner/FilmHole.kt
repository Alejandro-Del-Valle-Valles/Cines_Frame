package com.alejandro.proyecto_cines_frame.ui.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
@Composable
//Un hueco blanco para el BannerBorder
fun FilmHole(config: BannerBorderConfig) {
    Box(
        modifier = Modifier
            .padding(end = config.spacing)
            .width(config.holeWidth)
            .height(config.holeHeight)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(percent = 30)
            )
    )
}