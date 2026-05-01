package com.alejandro.proyecto_cines_frame

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.layout.MovieDetailDesktop
import com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets.EntradasListaModel
import com.alejandro.proyecto_cines_frame.ui.screen.MainScreen
import com.alejandro.proyecto_cines_frame.ui.screen.MovieDetailScreen
import com.alejandro.proyecto_cines_frame.ui.screen.UserProfileScreen
import com.alejandro.proyecto_cines_frame.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.banner
import proyecto_cines_frame.composeapp.generated.resources.logo_frames


@Composable
@Preview
fun App() {
    AppTheme {
        MainScreen()

        //TODO: Creo que está mal lo de la lista, por parte del modelo, pero no se me ocurre cómo debería ser
        //Porque pasandole un correo debebría debolver la lista con los datos de sólo esa persona,
        // pero esta es una lista con muchos correos y los muestra todos
        /*
        val entradas = listOf(
            EntradasListaModel(correo = "juan.perez@email.com"),
            EntradasListaModel(correo = "maria.garcia@email.com"),
            EntradasListaModel(correo = "carlos.lopez@email.com"),
            EntradasListaModel(correo = "ana.martin@email.com"),
            EntradasListaModel(correo = "lucia.fernandez@email.com"),
            EntradasListaModel(correo = "david.sanchez@email.com"),
            EntradasListaModel(correo = "sofia.ruiz@email.com"),
            EntradasListaModel(correo = "miguel.torres@email.com"),
            EntradasListaModel(correo = "elena.diaz@email.com"),
            EntradasListaModel(correo = "pablo.navarro@email.com"),
            EntradasListaModel(correo = "juan.perez@email.com"),
            EntradasListaModel(correo = "maria.garcia@email.com"),
            EntradasListaModel(correo = "carlos.lopez@email.com"),
            EntradasListaModel(correo = "ana.martin@email.com"),
            EntradasListaModel(correo = "lucia.fernandez@email.com"),
            EntradasListaModel(correo = "david.sanchez@email.com"),
            EntradasListaModel(correo = "sofia.ruiz@email.com"),
            EntradasListaModel(correo = "miguel.torres@email.com"),
            EntradasListaModel(correo = "elena.diaz@email.com"),
            EntradasListaModel(correo = "pablo.navarro@email.com")
        )

        UserProfileScreen("Pepito", entradas, {}, {}, {})

        */
    }
}
