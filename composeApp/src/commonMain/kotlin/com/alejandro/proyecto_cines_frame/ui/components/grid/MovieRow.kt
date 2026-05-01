package com.alejandro.proyecto_cines_frame.ui.components.grid

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.MovieCard

//Como significa Row, Esto lo que hacees organizar la disposicion horizontal de las peliculas uwu
@Composable
fun MovieRow(
    movies: List<Pelicula>,
    sessions: List<Sesion>,
    onMovieClick: (Pelicula) -> Unit = {},
    onSessionClick: (Sesion) -> Unit = {},
    columns: Int,
    cardWidth: Dp,
    posterHeight: Dp,
    sessionScale: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        movies.forEach { movie ->
            GridItem {
                MovieCard(
                    movie = movie,
                    sessions = sessions,
                    onMovieClick = onMovieClick,
                    onSessionClick = onSessionClick,
                    cardWidth = cardWidth,
                    posterHeight = posterHeight,
                    sessionScale = sessionScale
                )
            }
        }
        repeat(columns - movies.size) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
