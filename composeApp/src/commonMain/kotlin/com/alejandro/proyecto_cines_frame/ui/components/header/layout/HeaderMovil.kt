package com.alejandro.proyecto_cines_frame.ui.components.header.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderActions
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderBrand
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderSearchField

@Composable
fun HeaderMovil(
    alClickCartelera: () -> Unit,
    alClickCuenta: () -> Unit,
    alCambiarEstadoBusqueda: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HeaderBrand(
            esEscritorio = false,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                HeaderSearchField(
                    valor = "",
                    alCambiarValor = {},
                    alCambiarFoco = alCambiarEstadoBusqueda,
                    placeholder = "Buscar",
                    esCompacto = true
                )
            }

            Spacer(Modifier.width(16.dp))

            HeaderActions(
                alClickCartelera = alClickCartelera,
                alClickCuenta = alClickCuenta
            )
        }
    }
}