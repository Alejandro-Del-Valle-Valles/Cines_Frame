package com.alejandro.proyecto_cines_frame

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.layout.MovieDetailDesktop
import com.alejandro.proyecto_cines_frame.ui.screen.MainScreen
import com.alejandro.proyecto_cines_frame.ui.screen.MovieDetailScreen
import com.alejandro.proyecto_cines_frame.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.banner
import proyecto_cines_frame.composeapp.generated.resources.logo_frames

@Composable
@Preview
fun App() {
    AppTheme {
        //MainScreen()
        MaterialTheme {
            MovieDetailScreen(
                title = "Atrapando a un monstruo",
                description = "Aurora, una niña de 10 años, está convencida de que el monstruo que vive bajo su cama se ha comido a su familia...",
                directors = "Bryan Fuller",
                actors = "Mads Mikkelsen, David Dastmalchian, Sigourney Weaver, Sophie Sloan",
                duration = "105'",
                ageRating = "16 años",
                imagePainter = painterResource(Res.drawable.logo_frames), // usa el que tengas
                onBackClick = {}
            )
        }
    }
}
