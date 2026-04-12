package com.alejandro.proyecto_cines_frame.ui.components.footer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.footer.layout.FooterDesktop
import com.alejandro.proyecto_cines_frame.ui.components.footer.layout.FooterMobile
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader

// Footer principal en el que se elige si usa el específico para móvil o para escritorio
@Composable
fun Footer() {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorFondoHeader)
            .padding(16.dp)
    ) {

        val esEscritorio = FooterUtils.esEscritorio(maxWidth)

        if (esEscritorio) {
            FooterDesktop()
        } else {
            FooterMobile()
        }
    }
}