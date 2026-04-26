package com.alejandro.proyecto_cines_frame.ui.components.banner

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay

@Composable
fun Banner(
    images: List<String>,
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 4000L
) {
    if (images.isEmpty()) {
        Box(modifier.fillMaxWidth().height(560.dp)) {

        }
    }

    var currentIndex by remember(images) { mutableStateOf(0) }
    LaunchedEffect(images, autoSlideDuration) {
        if (images.size <= 1) return@LaunchedEffect
        while (true) {
            delay(autoSlideDuration)
            currentIndex = (currentIndex + 1) % images.size
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        BannerBorder()

        Box(modifier = Modifier.fillMaxWidth()) {
            AnimatedContent(targetState = currentIndex, label = "banner") { index ->
                BoxWithConstraints {

                    val bannerHeight = when {
                        maxWidth < 600.dp -> 220.dp
                        maxWidth < 900.dp -> 320.dp
                        maxWidth < 1400.dp -> 420.dp
                        else -> 500.dp
                    }

                    AsyncImage(
                        model = images[index],
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(bannerHeight),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        BannerBorder()
    }
}