package com.alejandro.proyecto_cines_frame.ui.components.header

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object HeaderUtils {
    val PuntoCorteEscritorio: Dp = 900.dp
    val AnchoMaximoBuscadorEscritorio: Dp = 460.dp
    val AltoBuscadorMovil: Dp = 52.dp
    val AltoBuscadorEscritorio: Dp = 48.dp
    fun paddingHorizontal(esEscritorio: Boolean): Dp = if (esEscritorio) 32.dp else 20.dp
    fun paddingVertical(esEscritorio: Boolean): Dp = if (esEscritorio) 14.dp else 12.dp
    fun tamanioLogo(esEscritorio: Boolean): Dp = if (esEscritorio) 48.dp else 40.dp
    //Valores para customizar el oscurecimiento del fondo de la busqueda
    const val OpacidadOscurecimiento: Float = 0.32f
    const val DuracionTransicionOscurecimientoMs: Int = 220
}