package com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun MovieManagementMovile(
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
            .padding(12.dp)
    ) {

        // HEADER FIJO

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Gestión de películas",
                color = TextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )

            Button(
                onClick = onAddMovie,
                colors = ButtonDefaults.buttonColors(
                    containerColor = OtroRojo
                ),
                shape = RectangleShape,
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                    vertical = 6.dp
                )
            ) {

                Text(
                    text = "Añadir película",
                    color = TextWhite,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LISTA SCROLLEABLE

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(movies) { movie ->

                Column {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // 🎬 NOMBRE
                        Column(
                            modifier = Modifier.weight(1.4f)
                        ) {

                            Text(
                                text = movie.nombre,
                                color = TextWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                maxLines = 2
                            )
                        }

                        // ⏱ DURACIÓN
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "Duración:",
                                color = TextWhite,
                                fontSize = 10.sp
                            )

                            Text(
                                text = movie.duracion.toString(),
                                color = TextWhite,
                                fontSize = 10.sp
                            )
                        }

                        // 🎞 ESTADO
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = when (movie.estado) {
                                    PeliculaEstado.CARTELERA -> "En cartelera"
                                    PeliculaEstado.ESTRENO -> "Estreno"
                                    PeliculaEstado.INACTIVA -> "Inactiva"
                                },
                                color = TextWhite,
                                fontSize = 10.sp
                            )
                        }

                        // ✏ BOTÓN MODIFICAR
                        Button(
                            onClick = {
                                onEditMovie(movie)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OtroRojo
                            ),
                            shape = RectangleShape,
                            modifier = Modifier.height(28.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {

                            Text(
                                text = "Modificar",
                                color = TextWhite,
                                fontSize = 10.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        // 🗑 ELIMINAR
                        IconButton(
                            onClick = {
                                onDeleteMovie(movie)
                            },
                            modifier = Modifier.size(22.dp)
                        ) {

                            Text(
                                text = "🗑",
                                color = OtroRojo,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(
                        color = TextWhite.copy(alpha = 0.7f),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}