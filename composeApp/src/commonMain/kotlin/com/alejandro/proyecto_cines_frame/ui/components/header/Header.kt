package com.alejandro.proyecto_cines_frame.ui.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alejandro.proyecto_cines_frame.ui.components.header.layout.HeaderEscritorio
import com.alejandro.proyecto_cines_frame.ui.components.header.layout.HeaderMovil
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
//Elige o movil o escritorio para poner la cabecera.
@Composable
@Preview
fun Header(
    modifier: Modifier = Modifier,
    alClickCartelera: () -> Unit = {},
    alClickCuenta: () -> Unit = {},
    alCambiarEstadoBusqueda: (Boolean) -> Unit = {}
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .background(ColorFondoHeader)
    ) {
        val esEscritorio = HeaderUtils.PuntoCorteEscritorio <= maxWidth

        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = HeaderUtils.paddingHorizontal(esEscritorio),
                    vertical = HeaderUtils.paddingVertical(esEscritorio)
                )
        ) {
            if (esEscritorio) {
                HeaderEscritorio(
                    alClickCartelera = alClickCartelera,
                    alClickCuenta = alClickCuenta,
                    alCambiarEstadoBusqueda = alCambiarEstadoBusqueda
                )
            } else {
                HeaderMovil(
                    alClickCartelera = alClickCartelera,
                    alClickCuenta = alClickCuenta,
                    alCambiarEstadoBusqueda = alCambiarEstadoBusqueda
                )
            }
        }
    }
}