// ui/components/MovieGrid.kt
package com.alejandro.proyecto_cines_frame.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Movie

/** 
 * Grid de películas reutilizable. 
 * Usamos un Column con Rows para evitar el error de Nested Scroll dentro de un LazyColumn.
 */
@Composable
fun MovieGrid(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    columns: Int = 3
) {
    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val rows = movies.chunked(columns)
        for (rowMovies in rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (movie in rowMovies) {
                    Box(modifier = Modifier.weight(1f)) {
                        MovieCard(movie = movie)
                    }
                }
                // Rellenar espacios vacíos si la fila no está completa
                if (rowMovies.size < columns) {
                    repeat(columns - rowMovies.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
