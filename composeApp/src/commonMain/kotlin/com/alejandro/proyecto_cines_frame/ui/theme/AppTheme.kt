package com.alejandro.proyecto_cines_frame.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

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
    ) {

        MaterialTheme(
            colorScheme = DarkColorScheme,
            typography = AppTypography,
            content = content
        )
    }
}