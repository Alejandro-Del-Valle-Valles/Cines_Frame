package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.layout.MovieDetailDesktop
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.layout.MovieDetailMovile
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.banner

@Composable
fun MovieDetailScreen(
    title: String,
    description: String,
    directors: String,
    actors: String,
    duration: String,
    ageRating: String,
    imagePainter: Painter,
    onBackClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorFondoHeader)
            .padding(16.dp)
    ) {

        val esEscritorio = FooterUtils.esEscritorio(maxWidth)

        if (esEscritorio) {
            MovieDetailDesktop(title, description, directors, actors, duration, ageRating, imagePainter, onBackClick)
        } else {
            MovieDetailMovile(title, description, directors, actors, duration, ageRating, imagePainter, onBackClick)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun PreviewMovieDetailScreen() {
    MaterialTheme {
        MovieDetailScreen(
            title = "Interstellar",
            description = "Un grupo de exploradores viaja a través de un agujero de gusano en el espacio en un intento por asegurar la supervivencia de la humanidad.",
            directors = "Christopher Nolan",
            actors = "Matthew McConaughey, Anne Hathaway",
            duration = "169 min",
            ageRating = "+12",
            imagePainter = painterResource(Res.drawable.banner), // Aquí va la imagen de prueba
            onBackClick = {}
        )
    }
}
