package com.alejandro.proyecto_cines_frame.ui.components.grid

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula

// Grid de películas que reparte las cards en filas según el espacio disponible, para carol, Esto decide el tamaño de la malla
@Composable
fun MovieGrid(
    movies: List<Pelicula>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {

        val cardSize = calculateCardSize(maxWidth)
        val columns = calculateColumns(maxWidth, cardSize.width)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
