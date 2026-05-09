package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Compra
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets.UserProfileDesktop
import com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets.UserProfileMovile
import com.alejandro.proyecto_cines_frame.ui.logic.presenter.ProfilePresenter
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader

@Composable
fun UserProfileScreen(
    compras: List<Compra>,
    movieTitlesById: Map<String, String>,
    errorMessage: String?,
    presenter: ProfilePresenter,
    onPasswordUpdated: () -> Unit,
    onBackClick: () -> Unit
) {
    val state = presenter.state.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.nameUpdated) {
        if (state.nameUpdated) {
            snackbarHostState.showSnackbar("Nombre actualizado con éxito")
            presenter.consumeNameUpdated()
        }
    }

    LaunchedEffect(state.passwordUpdated) {
        if (state.passwordUpdated) {
            snackbarHostState.showSnackbar("Contraseña actualizada con éxito.")
            presenter.consumePasswordUpdated()
            onPasswordUpdated()
        }
    }

    LaunchedEffect(state.downloadMessage) {
        val message = state.downloadMessage
        if (!message.isNullOrBlank()) {
            snackbarHostState.showSnackbar(message)
            presenter.consumeDownloadMessage()
        }
    }

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
                UserProfileDesktop(
                    compras,
                    movieTitlesById,
                    errorMessage,
                    presenter,
                    onBackClick
                )
            else
                UserProfileMovile(
                    compras,
                    movieTitlesById,
                    errorMessage,
                    presenter,
                    onBackClick
                )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}