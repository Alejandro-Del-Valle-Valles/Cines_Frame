package com.alejandro.proyecto_cines_frame.ui.components.admin.MainAdminScreens

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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.screen.botonesDesdeLista
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionComprasEntradas
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionCuentas
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionImagenesBaner
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionPeliculas
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionProductos
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionSalas
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionSesiones
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun MainAdminDesktop(
    onButtonClick: (String) -> Unit
) {

    val background = BackgroundDark
    val panelColor = ColorFondoHeader
    val buttonColor = OtroRojo

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        contentAlignment = Alignment.Center
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth(0.8f), // Se adapta al ancho de pantalla
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            // 🎬 PANEL IZQUIERDA
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(panelColor)
                    .padding(vertical = 32.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AdminPanel(
                    title = "Gestión del cine",
                    buttons = listOf(
                        txtBtnGestionImagenesBaner,
                        txtBtnGestionPeliculas,
                        txtBtnGestionSesiones,
                        txtBtnGestionSalas,
                        txtBtnGestionProductos
                    ),
                    buttonColor = buttonColor,
                    onButtonClick = onButtonClick
                )
            }
        }
    }
}

@Composable
fun AdminPanel(
    title: String,
    buttons: List<String>,
    buttonColor: Color,
    onButtonClick: (String) -> Unit
) {
    // TÍTULO
    Text(
        text = title,
        color = TextWhite,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(8.dp))

    Divider(
        color = TextWhite,
        thickness = 1.dp,
        modifier = Modifier.width(200.dp)
    )

    Spacer(modifier = Modifier.height(32.dp))

    // BOTONES
    botonesDesdeLista(
        buttons = buttons,
        buttonColor = buttonColor,
        onButtonClick = onButtonClick
    )
}