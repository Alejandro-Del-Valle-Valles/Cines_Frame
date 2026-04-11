package com.alejandro.proyecto_cines_frame.ui.components.features.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.components.grid.EmptyMoviesMessage
import com.alejandro.proyecto_cines_frame.ui.components.grid.MovieGrid
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
//Seccion de películas con título y grid debajo, como un mini apartado de catalogo bien ordenadito :3
@Composable
fun MovieSection(
    title: String,
    movies: List<Pelicula>,
    sessions: List<Sesion>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = TextWhite
        )

        Spacer(Modifier.height(8.dp))

        //mensaje vacio
        if (movies.isEmpty()) {
            EmptyMoviesMessage()
        } else {
            MovieGrid(
                movies = movies,
                sessions = sessions
            )
        }
    }
}
