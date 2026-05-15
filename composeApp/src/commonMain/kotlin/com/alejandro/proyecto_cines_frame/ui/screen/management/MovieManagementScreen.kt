package com.alejandro.proyecto_cines_frame.ui.screen.management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens.MovieManagementDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens.MovieManagementMovile
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader

@Suppress("UnusedBoxWithConstraintsScope")
@Composable
fun MovieManagementScreen(
    movies: List<Pelicula>,
    onAddMovie: () -> Unit,
    onEditMovie: (Pelicula) -> Unit,
    onDeleteMovie: (Pelicula) -> Unit,
    onBack: () -> Unit
) {

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {

        val esEscritorio =
            FooterUtils.esEscritorio(maxWidth)

        Box {

            BackButton(
                onClick = onBack,

                modifier = Modifier
                    .align(Alignment.TopStart)
                    .zIndex(10f)

                    // más abajo y más cómodo en móvil
                    .padding(

                        start = 16.dp,

                        top =
                            if (esEscritorio)
                                16.dp
                            else
                                32.dp
                    )
            )

            BoxWithConstraints(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorFondoHeader)

                    // deja espacio al botón en móvil
                    .padding(

                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,

                        top =
                            if (esEscritorio)
                                16.dp
                            else
                                90.dp
                    )
            ) {

                if (esEscritorio) {

                    MovieManagementDesktop(
                        movies,
                        onAddMovie,
                        onEditMovie,
                        onDeleteMovie
                    )

                } else {

                    MovieManagementMovile(
                        movies,
                        onAddMovie,
                        onEditMovie,
                        onDeleteMovie
                    )
                }
            }
        }
    }
}