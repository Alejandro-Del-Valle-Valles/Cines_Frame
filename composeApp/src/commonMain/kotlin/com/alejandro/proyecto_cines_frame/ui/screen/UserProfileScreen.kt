package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Entrada
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets.UserProfileDesktop
import com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets.UserProfileMovile
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader

@Composable
fun UseProfileScreen(
    userName: String,
    tickets: List<Entrada>,
    onChangeName: () -> Unit,
    onChangePassword: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorFondoHeader)
            .padding(16.dp)
    ) {

        val esEscritorio = FooterUtils.esEscritorio(maxWidth)

        if (esEscritorio) {
            UserProfileDesktop(userName, tickets, onChangeName, onChangePassword)
        } else {
            UserProfileMovile(userName, tickets, onChangeName, onChangePassword)
        }
    }
}