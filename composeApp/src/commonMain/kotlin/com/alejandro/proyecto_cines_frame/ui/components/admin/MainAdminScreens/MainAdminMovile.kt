package com.alejandro.proyecto_cines_frame.ui.components.admin.MainAdminScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.screen.background
import com.alejandro.proyecto_cines_frame.ui.screen.botonesDesdeLista
import com.alejandro.proyecto_cines_frame.ui.screen.buttonColor
import com.alejandro.proyecto_cines_frame.ui.screen.panelColor
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionComprasEntradas
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionCuentas
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionImagenesBaner
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionPeliculas
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionProductos
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionSalas
import com.alejandro.proyecto_cines_frame.ui.screen.txtBtnGestionSesiones
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun MainAdminMovile(
    onButtonClick: (String) -> Unit
) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        AdminPanelMobile(
            title = "Gestión del cine",
            buttons = listOf(
                txtBtnGestionPeliculas,
                txtBtnGestionSesiones,
                txtBtnGestionSalas,
                txtBtnGestionProductos
            ),
            buttonColor = buttonColor,
            panelColor = panelColor,
            onButtonClick = onButtonClick
        )

        AdminPanelMobile(
            title = "Gestión de datos\nde la aplicación",
            buttons = listOf(
                txtBtnGestionCuentas,
                txtBtnGestionImagenesBaner,
                txtBtnGestionComprasEntradas
            ),
            buttonColor = buttonColor,
            panelColor = panelColor,
            onButtonClick = onButtonClick
        )
    }
}

@Composable
fun AdminPanelMobile(
    title: String,
    buttons: List<String>,
    buttonColor: Color,
    panelColor: Color,
    onButtonClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(panelColor)
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // TÍTULO
        Text(
            text = title,
            color = TextWhite,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Divider(
            color = TextWhite,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // BOTONES
        botonesDesdeLista(
            buttons = buttons,
            buttonColor = buttonColor,
            onButtonClick = onButtonClick
        )
    }
}