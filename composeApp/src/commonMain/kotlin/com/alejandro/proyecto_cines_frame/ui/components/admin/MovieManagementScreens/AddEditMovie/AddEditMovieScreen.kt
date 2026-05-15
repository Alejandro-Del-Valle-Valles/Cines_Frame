package com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens.AddEditMovie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader

@Composable
fun AddEditMovieScreen(
    movie: Pelicula?, // 🔥 null = añadir | con datos = modificar
    onSave: (Pelicula) -> Unit,
    onDeleteGenre: (PeliculaGenero) -> Unit,
    onSelectImage: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorFondoHeader)
            .padding(16.dp)
    ) {

    val esEscritorio = FooterUtils.esEscritorio(maxWidth)

    if (esEscritorio) {
        AddEditMovieDesktop(movie, onSave, onDeleteGenre, onSelectImage)
    } else {
        AddEditMovieMovile(movie, onSave, onDeleteGenre, onSelectImage)
    }
        }

}