package com.alejandro.proyecto_cines_frame.ui.components.filter

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
//Utilidades para los layout filtros, porfa no seais cochino y me lieis
object FilterLayoutUtils {

    val PuntoCorteEscritorio: Dp = 400.dp

    fun esEscritorio(maxWidth: Dp): Boolean {
        return maxWidth >= PuntoCorteEscritorio
    }

    fun paddingHorizontal(esEscritorio: Boolean): Dp =
        if (esEscritorio) 24.dp else 12.dp

    fun paddingVertical(esEscritorio: Boolean): Dp =
        if (esEscritorio) 12.dp else 8.dp
}
