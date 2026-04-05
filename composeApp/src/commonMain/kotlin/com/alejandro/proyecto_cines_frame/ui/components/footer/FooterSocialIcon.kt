package com.alejandro.proyecto_cines_frame.ui.components.footer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
//Icono de social medias, fotito clickeable que te lleva a un link
@Composable
fun FooterSocialIcon(
    painter: Painter,
    contentDescription: String,
    url: String
) {
    val uriHandler = LocalUriHandler.current

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(24.dp)
            .clickable {
                uriHandler.openUri(url)
            }
    )
}