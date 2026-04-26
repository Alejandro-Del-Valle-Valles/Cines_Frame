package com.alejandro.proyecto_cines_frame.ui.components.session

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.theme.SurfaceDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

//Chip individual de sesión con la hora, vamos donde esta la hora de las pelis

@Composable
fun SessionChip(
    session: Sesion,
    text: String,
    scale: Float = 1f,
    showIcons: Boolean = true,
    fillWidth: Boolean = false,
    onClick: (Sesion) -> Unit = {}
) {
    val textStyle =
        if (scale > 1.1f) MaterialTheme.typography.labelMedium
        else MaterialTheme.typography.labelSmall

    Box(
        modifier = Modifier
            .then(if (fillWidth) Modifier.fillMaxWidth() else Modifier)
            .clickable { if(session.pelicula.estado == PeliculaEstado.CARTELERA) onClick(session) }
            .background(
                color = SurfaceDark,
                shape = RoundedCornerShape(4.dp * scale)
            )
            .padding(
                horizontal = 6.dp * scale,
                vertical = 3.dp * scale
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text + if (showIcons) sessionIcons(session) else "",
            color = TextWhite,
            style = textStyle,
            textAlign = TextAlign.Center
        )
    }
}

private fun sessionIcons(session: Sesion): String {
    return when {
        session.tresD && session.vose -> "🥽🌍"
        session.tresD -> "🥽"
        session.vose -> "🌍"
        else -> "🎞️"
    }
}