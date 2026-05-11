package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alejandro.proyecto_cines_frame.ui.components.admin.MainAdminScreens.MainAdminDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.MainAdminScreens.MainAdminMovile
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

val background = BackgroundDark
val panelColor = ColorFondoHeader
val buttonColor = OtroRojo

//Gestión del cine
val txtBtnGestionPeliculas = "Gestionar películas"
val txtBtnGestionSesiones = "Gestionar sesiones"
val txtBtnGestionSalas = "Gestionar salas"
val txtBtnGestionProductos = "Gestionar productos"
//Gestión de datos de la aplicación
val txtBtnGestionCuentas = "Gestionar cuentas"
val txtBtnGestionImagenesBaner = "Gestionar imagenes de baner"
val txtBtnGestionComprasEntradas = "Gestionar compras de entradas"

@Composable
fun MainAdminScreen(
    onBack: () -> Unit,
    onManageMovies: () -> Unit,
    onManageRooms: () -> Unit,
    onManageBanners: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .zIndex(10f)
                .padding(16.dp)
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorFondoHeader)
                .padding(16.dp)
        ) {

            val esEscritorio = FooterUtils.esEscritorio(maxWidth)
            val onAdminButtonClick: (String) -> Unit = { textoBoton ->
                if (textoBoton == txtBtnGestionPeliculas) {
                    onManageMovies()
                } else if (textoBoton == txtBtnGestionSalas) {
                    onManageRooms()
                } else if (textoBoton == txtBtnGestionImagenesBaner) {
                    onManageBanners()
                }
            }

            if (esEscritorio)
                MainAdminDesktop(onButtonClick = onAdminButtonClick)
            else
                MainAdminMovile(onButtonClick = onAdminButtonClick)
        }
    }
}

@Composable
fun botonesDesdeLista(
    buttons: List<String>,
    buttonColor: Color,
    onButtonClick: (String) -> Unit
) {
    buttons.forEach { text ->

        Button(
            onClick = { onButtonClick(text) },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier
                .fillMaxWidth(0.99f)
                .height(45.dp)
        ) {
            Text(text, color = TextWhite)
        }

        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun redireccionDeBotones( textoBoton : String) {
    if (textoBoton == txtBtnGestionPeliculas) {

    }
    else if (textoBoton == txtBtnGestionSesiones) {

    }
    //O un switch case, pero como aún no existen las pantallas... TODO
}
