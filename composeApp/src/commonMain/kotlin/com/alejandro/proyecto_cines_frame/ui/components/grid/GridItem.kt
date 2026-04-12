package com.alejandro.proyecto_cines_frame.ui.components.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//controla el tamanio de tot sea igual
@Composable
fun RowScope.GridItem(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.TopCenter
    ) {
        content()
    }
}
