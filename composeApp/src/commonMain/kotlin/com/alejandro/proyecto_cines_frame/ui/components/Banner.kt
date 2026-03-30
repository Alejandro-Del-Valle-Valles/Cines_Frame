package com.alejandro.proyecto_cines_frame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.banner
@Composable
fun Banner() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(Res.drawable.banner),
            contentDescription = "Banner principal",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit  // Ajuste sin recorte【6†L31-L39】
        )
    }
}
