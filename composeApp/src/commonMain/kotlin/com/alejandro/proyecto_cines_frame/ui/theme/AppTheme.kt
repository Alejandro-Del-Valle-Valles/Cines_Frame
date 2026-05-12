package com.alejandro.proyecto_cines_frame.ui.theme

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(

    primary = PrimaryRed,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextWhite,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(

        LocalScrollbarStyle provides ScrollbarStyle(

            minimalHeight = 16.dp,
            thickness = 10.dp,
            shape = RoundedCornerShape(8.dp),
            hoverDurationMillis = 300,
            unhoverColor =
                Color.Gray.copy(alpha = 0.45f),
            hoverColor = OtroRojo
        )
    ) {

        MaterialTheme(
            colorScheme = DarkColorScheme,
            typography = AppTypography,
            content = content
        )
    }
}