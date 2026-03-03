package com.alejandro.proyecto_cines_frame

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Proyecto_Cines_Frame",
    ) {
        App()
    }
}