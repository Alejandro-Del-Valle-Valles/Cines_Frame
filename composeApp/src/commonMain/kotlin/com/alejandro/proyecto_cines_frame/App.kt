package com.alejandro.proyecto_cines_frame

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.ui.screen.MainAdminScreen
import com.alejandro.proyecto_cines_frame.ui.screen.MainScreen
import com.alejandro.proyecto_cines_frame.ui.screen.MovieManagementScreen
import com.alejandro.proyecto_cines_frame.ui.theme.AppTheme
import kotlinx.datetime.LocalTime


@Composable
@Preview
fun App() {
    AppTheme {
        //MainScreen()

        val mockMovies = listOf( //TODO: esto debe ser get getPelículas de la API

            Pelicula(
                id = "1",
                nombre = "Destino final: Lazos de sangre",
                descripcion = "Descripción",
                estado = PeliculaEstado.CARTELERA,
                portada = null,
                duracion = LocalTime(1, 34),
                calificacionEdad = 18,
                genero = PeliculaGenero.ACCION
            ),

            Pelicula(
                id = "2",
                nombre = "Interstellar",
                descripcion = "Descripción",
                estado = PeliculaEstado.ESTRENO,
                portada = null,
                duracion = LocalTime(2, 49),
                calificacionEdad = 12,
                genero = PeliculaGenero.CIENCIA_FICCION
            ),

            Pelicula(
                id = "3",
                nombre = "Batman",
                descripcion = "Descripción",
                estado = PeliculaEstado.CARTELERA,
                portada = null,
                duracion = LocalTime(2, 10),
                calificacionEdad = 16,
                genero = PeliculaGenero.ACCION
            ),

            Pelicula(
                id = "4",
                nombre = "Mario Galaxy",
                descripcion = "Descripción",
                estado = PeliculaEstado.ESTRENO,
                portada = null,
                duracion = LocalTime(1, 38),
                calificacionEdad = 7,
                genero = PeliculaGenero.COMEDIA
            )
        )
        var movieToDelete by remember { mutableStateOf<Pelicula?>(null) }


        MaterialTheme {

            MovieManagementScreen(
                movies = mockMovies,

                onAddMovie = {
                    println("Añadir película") //TODO: llevar a la pantalla de formulario de creación de película
                },

                onEditMovie = { pelicula ->
                    println("Editar: ${pelicula.nombre}") //TODO: llevar a la pantalla de formulario de edición
                },

                // 🔥 Al pulsar eliminar abrimos popup
                onDeleteMovie = { pelicula ->
                    movieToDelete = pelicula
                }
            )
        }// 🔥 POPUP CONFIRMACIÓN
        if (movieToDelete != null) {

            AlertDialog(
                onDismissRequest = {
                    movieToDelete = null
                },

                title = {
                    Text("Confirmar borrado")
                },

                text = {
                    Text(
                        "¿Seguro que quieres eliminar \"${movieToDelete!!.nombre}\"?"
                    )
                },

                confirmButton = {

                    Button(
                        onClick = {

                            println("ELIMINADA: ${movieToDelete!!.nombre}") //TODO: Aquí habría que hacer el delete con la API y recargar la pantalla para que se refleje el borrado

                            // cerrar popup
                            movieToDelete = null
                        }
                    ) {
                        Text("Eliminar")
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {
                            movieToDelete = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
