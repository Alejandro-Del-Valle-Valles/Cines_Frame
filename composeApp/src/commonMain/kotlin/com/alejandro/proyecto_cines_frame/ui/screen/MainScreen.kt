package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.MovieStatus
import com.alejandro.proyecto_cines_frame.ui.components.banner.Banner
import com.alejandro.proyecto_cines_frame.ui.components.Footer
import com.alejandro.proyecto_cines_frame.ui.components.Header
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.MovieMockData
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.MovieSection
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.banner

 //Pantalla principal

@Composable
fun MainScreen() {

    val allMovies = MovieMockData.getMovies()

    val carteleraMovies = allMovies.filter { it.status == MovieStatus.CARTELERA }
    val estrenosMovies = allMovies.filter { it.status == MovieStatus.ESTRENO }

    LazyColumn(
        modifier = Modifier.background(BackgroundDark)
    ) {

        item { Header() }
        item {
            Banner(
                images = listOf(
                    painterResource(Res.drawable.banner)
                )
            )
        }

        item {
            MovieSection(
                title = "EN CARTELERA",
                movies = carteleraMovies
            )
        }

        item {
            MovieSection(
                title = "PRÓXIMOS ESTRENOS",
                movies = estrenosMovies
            )
        }

        item {
            Spacer(Modifier.height(24.dp))
            Footer()
        }
    }
}
