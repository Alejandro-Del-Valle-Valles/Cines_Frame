package com.alejandro.proyecto_cines_frame.ui.components.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//agrupa las acciones del header, como “Cartelera” y “Mi cuenta”, para no dejarlas sueltas por ahi, conclusion "Que me quedo sin comer"
@Composable
fun HeaderActions(
    alClickCartelera: () -> Unit,
    alClickCuenta: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderActionButton(
            texto = "Cartelera",
            alClick = alClickCartelera
        )
        HeaderActionButton(
            texto = "Mi cuenta",
            alClick = alClickCuenta
        )
    }
}