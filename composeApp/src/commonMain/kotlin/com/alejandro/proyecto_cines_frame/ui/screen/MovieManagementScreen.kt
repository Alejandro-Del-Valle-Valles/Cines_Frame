package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens.MovieManagementDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens.MovieManagementMovile
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader

@Composable
fun MovieManagementScreen(
    movies: List<Pelicula>,
    onAddMovie: () -> Unit,
    onEditMovie: (Pelicula) -> Unit,
    onDeleteMovie: (Pelicula) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BoxWithConstraints (
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorFondoHeader)
                .padding(16.dp)
        ) {

            val esEscritorio = FooterUtils.esEscritorio(maxWidth)

            if (esEscritorio) {
                MovieManagementDesktop(movies, onAddMovie, onEditMovie, onDeleteMovie)
            } else {
                MovieManagementMovile(movies, onAddMovie, onEditMovie, onDeleteMovie)
            }
        }
    }
}