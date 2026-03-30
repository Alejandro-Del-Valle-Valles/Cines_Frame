package com.alejandro.proyecto_cines_frame.ui.components.grid

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Movie

//Ventana Principal del Grid, de aqui se llama a MovieRow y MovieCard para mostrar las peliculas

@Composable
fun MovieGrid(
    movies: List<Movie>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {

        val cardSize = calculateCardSize(maxWidth)
        val columns = calculateColumns(maxWidth, cardSize.width)

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            movies.chunked(columns).forEach { rowMovies ->
                MovieRow(
                    movies = rowMovies,
                    columns = columns,
                    cardWidth = cardSize.width,
                    posterHeight = cardSize.posterHeight,
                    sessionScale = cardSize.scale
                )
            }
        }
    }
}