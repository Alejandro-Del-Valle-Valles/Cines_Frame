// ui/components/MovieSection.kt
package com.alejandro.proyecto_cines_frame.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Movie
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

/** Sección de películas con título y grid. */
@Composable
fun MovieSection(title: String, movies: List<Movie>) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = TextWhite
        )
        Spacer(Modifier.height(8.dp))
        MovieGrid(movies = movies)
    }
}
