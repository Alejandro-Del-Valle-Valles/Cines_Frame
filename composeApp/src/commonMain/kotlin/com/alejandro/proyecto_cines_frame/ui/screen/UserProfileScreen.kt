package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Compra
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets.UserProfileDesktop
import com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets.UserProfileMovile
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader

@Composable
fun UserProfileScreen(
    userName: String,
    compras: List<Compra>,
    movieTitlesById: Map<String, String>,
    errorMessage: String?,
    onChangeName: () -> Unit,
    onChangePassword: () -> Unit,
    onNameChanged: (String) -> Unit,
    onBackClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorFondoHeader)
            .padding(16.dp)
    ) {

        val esEscritorio = FooterUtils.esEscritorio(maxWidth)

        if (esEscritorio)
            UserProfileDesktop(
                userName,
                compras,
                movieTitlesById,
                errorMessage,
                onChangeName,
                onChangePassword,
                onNameChanged,
                onBackClick
            )
        else
            UserProfileMovile(
                userName,
                compras,
                movieTitlesById,
                errorMessage,
                onChangeName,
                onChangePassword,
                onNameChanged,
                onBackClick
            )
    }
}