package com.alejandro.proyecto_cines_frame

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.repository.PeliculaRepository
import com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens.AddEditMovie.AddEditMovieDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens.MovieManagementDesktop
import com.alejandro.proyecto_cines_frame.ui.screen.MainScreen
import com.alejandro.proyecto_cines_frame.ui.screen.management.ManageSessionsScreen
import com.alejandro.proyecto_cines_frame.ui.theme.AppTheme


@Composable
@Preview
fun App() {
    AppTheme {
       //MainScreen()
        AddEditMovieDesktop(null, {}, {}, {}, {})
    }
}
