package com.alejandro.proyecto_cines_frame.ui.components.header.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderActions
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderBrand
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderSearchField
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderUtils

@Composable
fun HeaderEscritorio(
    alClickCartelera: () -> Unit,
    alClickCuenta: () -> Unit,
    alCambiarEstadoBusqueda: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        HeaderBrand(
            esEscritorio = true,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier.Companion
                .width(HeaderUtils.AnchoMaximoBuscadorEscritorio)
                .zIndex(2f)
        ) {
            HeaderSearchField(
                valor = "",
                alCambiarValor = {},
                alCambiarFoco = alCambiarEstadoBusqueda,
                placeholder = "Buscar películas",
                esCompacto = false
            )
        }

        Spacer(Modifier.width(24.dp))

        HeaderActions(
            alClickCartelera = alClickCartelera,
            alClickCuenta = alClickCuenta
        )
    }
}