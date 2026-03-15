package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alejandro.proyecto_cines_frame.ui.components.*

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF141414))
    ) {

        Header()
        Banner()

        MovieSection(
            title = "EN CARTELERA"
        )
        MovieSection(
            title = "PRÓXIMOS ESTRENOS"
        )

        Spacer(Modifier.weight(1f))
        Footer()
    }
}