package com.alejandro.proyecto_cines_frame.ui.components.features.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import kotlinx.datetime.LocalTime

/**
 * Tarjeta de película. Por el momento es solo el equeleto
 */
@Composable
fun MovieCard(
    movie: Pelicula,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 110.dp,
    posterHeight: Dp = 160.dp,
    sessionScale: Float = 1f
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Column(
        modifier = modifier.width(cardWidth)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(posterHeight)
                .hoverable(interactionSource)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Gray, RoundedCornerShape(12.dp))
            )

            if (isHovered) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = movie.nombre,
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp * sessionScale))

        /*
        if (movie.sesiones.isNotEmpty()) {
            SessionRow(
                sesions = movie.sesiones,
                scale = sessionScale
            )
        }
         */
    }
}

@Preview
@Composable
fun MovieCardPreview() {
    MovieCard(
        movie = Pelicula(
            id = "ejemplo",
            nombre = "Movie Title",
            descripcion = "Example",
            estado = PeliculaEstado.CARTELERA,
            duracion = LocalTime(1, 30),
            genero = PeliculaGenero.AVENTURA
        )
    )
}