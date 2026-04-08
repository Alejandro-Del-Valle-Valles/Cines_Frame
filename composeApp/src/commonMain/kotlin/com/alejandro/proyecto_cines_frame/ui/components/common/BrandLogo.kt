package com.alejandro.proyecto_cines_frame.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderUtils
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.logo_frames
//marca visual de la marca: logo + nombre de la app
@Composable
fun HeaderBrand(
    esEscritorio: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (esEscritorio) Arrangement.Start else Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.logo_frames),
            contentDescription = "Logo de Cines Frames",
            modifier = Modifier.Companion.width(HeaderUtils.tamanioLogo(esEscritorio)),
            contentScale = ContentScale.Fit
        )

        Spacer(Modifier.width(12.dp))

        Text(
            text = "CINES FRAMES",
            color = TextWhite,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Italic
            )
        )
    }
}