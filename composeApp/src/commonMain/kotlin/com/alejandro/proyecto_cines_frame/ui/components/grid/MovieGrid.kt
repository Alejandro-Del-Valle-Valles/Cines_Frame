package com.alejandro.proyecto_cines_frame.ui.components.grid

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Movie
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.MovieCard
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun MovieGrid(
    movies: List<Movie>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth() // 🔥 IMPORTANTE
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