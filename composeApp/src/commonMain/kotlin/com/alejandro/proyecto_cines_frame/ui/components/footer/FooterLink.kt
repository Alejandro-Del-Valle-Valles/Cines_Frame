package com.alejandro.proyecto_cines_frame.ui.components.footer

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
//pata link texto clickeable muy guay
@Composable
fun FooterLink(
    text: String,
    onClick: () -> Unit = {}
) {
    Text(
        text = text,
        color = TextGray,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.clickable { onClick() }
    )
}