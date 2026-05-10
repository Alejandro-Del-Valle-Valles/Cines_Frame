package com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import java.util.Locale
import java.util.Locale.getDefault

@Composable
fun MovieManagementDesktop(
    movies: List<Pelicula>,
    onAddMovie: () -> Unit,
    onEditMovie: (Pelicula) -> Unit,
    onDeleteMovie: (Pelicula) -> Unit
) {

    val background = BackgroundDark
    val panelColor = ColorFondoHeader

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(32.dp)
    ) {

        // 🔝 HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = "Gestión de películas",
                    color = TextWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline                )
            }

            Button(
                onClick = onAddMovie,
                colors = ButtonDefaults.buttonColors(containerColor = OtroRojo),
                modifier = Modifier
                    .width(320.dp)
                    .height(48.dp)
            ) {
                Text(
                    "Añadir película",
                    color = TextWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // PANEL PELÍCULAS
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(panelColor)
                .padding(20.dp)
        ) {

            // SOLO ESTA ZONA SCROLLEA
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(movies) { movie ->

                    MovieRowDesktop(
                        movie = movie,
                        onEdit = { onEditMovie(movie) },
                        onDelete = { onDeleteMovie(movie) }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieRowDesktop(
    movie: Pelicula,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 🎬 TÍTULO
            Text(
                text = movie.nombre,
                color = TextWhite,
                modifier = Modifier.weight(2f),
                style = MaterialTheme.typography.bodyLarge
            )

            // ⏱ DURACIÓN
            Text(
                text = "Duración: ${movie.duracion}",
                color = TextWhite,
                modifier = Modifier.weight(1f)
            )

            // 🎟 ESTADO
            Text(
                text = movie.estado.toString().lowercase(getDefault()),
                color = TextWhite,
                modifier = Modifier.weight(1f)
            )

            // BOTÓN MODIFICAR
            Button(
                onClick = onEdit,
                colors = ButtonDefaults.buttonColors(containerColor = OtroRojo),
                modifier = Modifier
                    .width(140.dp)
                    .height(38.dp)
            ) {
                Text(
                    "Modificar",
                    color = TextWhite
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // 🗑 BORRAR
            IconButton(
                onClick = onDelete
            ) {

                Text(
                    text = "🗑",
                    color = OtroRojo
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Divider(
            color = TextWhite.copy(alpha = 0.7f),
            thickness = 1.dp
        )
    }
}