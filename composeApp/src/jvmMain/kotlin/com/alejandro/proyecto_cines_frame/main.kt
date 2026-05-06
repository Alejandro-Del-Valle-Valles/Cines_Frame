package com.alejandro.proyecto_cines_frame

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.logo_frames_bg

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Proyecto_Cines_Frame",
        icon = painterResource(Res.drawable.logo_frames_bg)
    ) {
        App()
    }
}