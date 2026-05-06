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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.admin.MainAdminScreens.MainAdminDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.MainAdminScreens.MainAdminMovile
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
fun MainAdminScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorFondoHeader)
                .padding(16.dp)
        ) {

            val esEscritorio = FooterUtils.esEscritorio(maxWidth)

            if (esEscritorio)
                MainAdminDesktop()
            else
                MainAdminMovile()
        }
    }
}

@Composable
fun botonesDesdeLista(
    buttons: List<String>,
    buttonColor: Color
) {
    buttons.forEach { text ->

        Button(
            onClick = { /* TODO navegación */ },  //Usar el metodo creado abajo, hay que completar tam
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


