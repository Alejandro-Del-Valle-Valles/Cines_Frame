package com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens.AddEditMovie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCreateDTO
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

private val PuntoCorteMovieEditor: Dp = 1200.dp

private fun esEscritorioMovieEditor(
    maxWidth: Dp
): Boolean {

    return maxWidth >= PuntoCorteMovieEditor
}

@Composable
fun AddEditMovieScreen(
    movie: Pelicula?,
    onSave: (PeliculaCreateDTO) -> Unit,
    onBack: () -> Unit
) {

    BoxWithConstraints(

        modifier = Modifier
            .fillMaxSize()
            .background(ColorFondoHeader)
    ) {

        val esEscritorio =
            esEscritorioMovieEditor(maxWidth)

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                BackButton(
                    onClick = onBack,

                    size =
                        if (esEscritorio)
                            42.dp
                        else
                            54.dp
                )

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Text(
                    text =
                        if (movie == null)
                            "Añadir película"
                        else
                            "Modificar película",

                    color = TextWhite,

                    style =
                        MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold,

                    textDecoration =
                        TextDecoration.Underline
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            if (esEscritorio) {

                AddEditMovieDesktop(
                    movie,
                    onSave
                )

            } else {

                AddEditMovieMovile(
                    movie,
                    onSave
                )
            }
        }
    }
}