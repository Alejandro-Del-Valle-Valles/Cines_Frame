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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.components.session.SessionRow

/**
 * Tarjeta de película.
 */
@Composable
fun MovieCard(
    movie: Pelicula,
    sessions: List<Sesion>,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 110.dp,
    posterHeight: Dp = 160.dp,
    sessionScale: Float = 1f
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val movieSessions = remember(sessions, movie.id) {
        sessions.filter { it.pelicula.id == movie.id }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(posterHeight)
                .hoverable(interactionSource)
        ) {
            if (!movie.portada.isNullOrBlank()) {
                AsyncImage(
                    model = movie.portada,
                    contentDescription = "Portada de ${movie.nombre}",
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.DarkGray),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sin imagen",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

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

        if (movieSessions.isNotEmpty()) {
            SessionRow(
                sessions = movieSessions,
                scale = sessionScale
            )
        }
    }
}