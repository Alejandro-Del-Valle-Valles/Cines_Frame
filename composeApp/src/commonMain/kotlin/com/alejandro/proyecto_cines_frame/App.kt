    package com.alejandro.proyecto_cines_frame

    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.tooling.preview.Preview
    import com.alejandro.proyecto_cines_frame.ui.screen.CheckoutScreen
    import com.alejandro.proyecto_cines_frame.ui.screen.MainScreen
    import com.alejandro.proyecto_cines_frame.ui.theme.AppTheme
    import kotlinx.datetime.LocalTime
    import kotlinx.datetime.TimeZone
    import kotlinx.datetime.toLocalDateTime
    import kotlin.time.Clock

    /*
        @Composable
        @Preview
        fun App() {
            AppTheme {
                MainScreen()
            }
        }
    */
    @Composable
    @Preview
    fun App() {
        AppTheme {

            var currentScreen by remember { mutableStateOf("checkout") }

            if (currentScreen == "main") {
                MainScreen()
            } else {

                val pelicula = com.alejandro.proyecto_cines_frame.domain.model.Pelicula(
                    id = "1",
                    nombre = "Dune",
                    descripcion = "Test",
                    estado = com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado.CARTELERA,
                    portada = null,
                    duracion = LocalTime(2, 30),
                    genero = com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero.ACCION
                )

                val sala = com.alejandro.proyecto_cines_frame.domain.model.Sala(
                    numero = 1,
                    aforo = 100
                )

                val sesion = com.alejandro.proyecto_cines_frame.domain.model.Sesion(
                    sala = sala,
                    pelicula = pelicula,
                    horario = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()),
                    tresD = true,
                    vose = true
                )

                val seatMatrix = listOf(
                    listOf(null, true, true, null, true, true, true, true, true),
                    listOf(true, false, true, null, true, false, true, true, true),
                    listOf(true, true, true, null, true, true, true, false, true),
                    listOf(true, true, true, null, true, true, true, true, true),
                    listOf(true, true, true, null, true, true, true, true, true)
                )

                CheckoutScreen(
                    session = sesion,
                    seatMatrix = seatMatrix,
                    onBack = {
                        currentScreen = "main"
                    }
                )
            }
        }
    }