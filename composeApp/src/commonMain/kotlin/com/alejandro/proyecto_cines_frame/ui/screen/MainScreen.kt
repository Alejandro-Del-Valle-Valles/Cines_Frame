package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Movie
import com.alejandro.proyecto_cines_frame.domain.model.MovieStatus
import com.alejandro.proyecto_cines_frame.ui.components.Banner
import com.alejandro.proyecto_cines_frame.ui.components.Footer
import com.alejandro.proyecto_cines_frame.ui.components.Header
import com.alejandro.proyecto_cines_frame.ui.components.MovieSection
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark

/**
 * Pantalla principal.
 *
 * IMPORTANTE:
 * - Solo hay UN scroll (LazyColumn)
 * - Evitamos nested scroll (error que tenías)
 */
@Composable
fun MainScreen() {

    // ⚠️ Esto luego irá a ViewModel (no aquí)
    val allMovies = listOf(
        Movie(1, "Película A", MovieStatus.CARTELERA),
        Movie(2, "Película B", MovieStatus.CARTELERA),
        Movie(3, "Película C", MovieStatus.ESTRENO),
        Movie(4, "Película D", MovieStatus.ESTRENO),
        Movie(5, "Película E", MovieStatus.CARTELERA),
        Movie(1, "Película F", MovieStatus.CARTELERA)

    )

    val carteleraMovies = allMovies.filter { it.status == MovieStatus.CARTELERA }
    val estrenosMovies = allMovies.filter { it.status == MovieStatus.ESTRENO }

    LazyColumn(
        modifier = Modifier
            .background(BackgroundDark)
    ) {

        item { Header() }
        item { Banner() }

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
