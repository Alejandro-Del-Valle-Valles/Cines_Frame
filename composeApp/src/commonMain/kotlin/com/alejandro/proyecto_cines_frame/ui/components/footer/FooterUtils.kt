package com.alejandro.proyecto_cines_frame.ui.components.footer

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
//utilidades patas
object FooterUtils {
    val PuntoCorteEscritorio: Dp = 700.dp

    fun esEscritorio(maxWidth: Dp): Boolean {
        return maxWidth >= PuntoCorteEscritorio
    }
}