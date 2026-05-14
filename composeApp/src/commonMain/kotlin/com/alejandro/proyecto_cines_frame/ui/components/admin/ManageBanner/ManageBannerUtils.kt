package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ManageBannerUtils {

    val PuntoCorteEscritorio: Dp = 700.dp

    fun esEscritorio(
        maxWidth: Dp
    ): Boolean {

        return maxWidth >= PuntoCorteEscritorio
    }
}