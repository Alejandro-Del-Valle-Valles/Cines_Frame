package com.alejandro.proyecto_cines_frame.ui.components.banner

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.delay


@Composable
fun Banner(images: List<Painter>, modifier: Modifier = Modifier, autoSlideDuration: Long = 4000L) {
    var currentIndex by remember { mutableStateOf(0) }

    //Esto crea la animación de pasar de imagen en el banner
    if (images.size > 1) {
        LaunchedEffect(images) {
            while (true) {
                delay(autoSlideDuration)
                currentIndex = getNextIndex(currentIndex, images.size)
            }
        }
    }

    //El banner en sí mismo está construido aquí
    Column(modifier = modifier.fillMaxWidth()) {
        //Borde de arriba
        BannerBorder()

        //Imagen del banner
        Box(modifier = Modifier.fillMaxWidth()) {
            AnimatedContent(targetState = currentIndex, label = "banner") { index ->
                Image(
                    painter = images[index],
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        //Borde de abajo
        BannerBorder()
    }
}