package com.alejandro.proyecto_cines_frame.ui.screen.management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorSesionApi
import com.alejandro.proyecto_cines_frame.data.remote.client.HttpClientFactory
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCrudDTO
import com.alejandro.proyecto_cines_frame.data.repository.SesionRepositoryImpl
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.domain.repository.SesionRepository
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.*
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import kotlinx.coroutines.launch

@Composable
fun ManageSessionsScreen(
    onBack: () -> Unit,
    sesionRepository: SesionRepository? = null
) {
    val repository = sesionRepository ?: remember {
        SesionRepositoryImpl(
            api = KtorSesionApi(HttpClientFactory.create())
        )
    }

    var state by remember {
        mutableStateOf(
            SessionUiState()
        )
    }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var sessionToDelete by remember { mutableStateOf<Sesion?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(repository) {
        isLoading = true
        errorMessage = null

        when (val result = repository.getAll()) {
            is ApiResult.Success -> {
                state = state.copy(sessions = result.data)
            }

            is ApiResult.Error -> {
                state = state.copy(sessions = emptyList())
                errorMessage = "Error al cargar sesiones"
            }
        }

        isLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        BackButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .zIndex(100f)
                .padding(16.dp)
        )

        ManageSessions(
            state = state,
            onAddSession = {},
            onDeleteSession = { session ->
                sessionToDelete = session
            }
        )

        if (errorMessage != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 72.dp)
            ) {
                Text(errorMessage ?: "", color = TextWhite)
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundDark.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = TextWhite)
            }
        }
    }


    if (sessionToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                sessionToDelete = null
            },
            title = {
                Text("Confirmar borrado")
            },
            text = {
                Text("¿Seguro que quieres eliminar la sesión?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val session = sessionToDelete
                        sessionToDelete = null
                        if (session != null) {
                            scope.launch {
                                when (
                                    val result = repository.deleteSesion(
                                        SesionCrudDTO(
                                            numSala = session.numSala,
                                            tresD = session.tresD,
                                            vose = session.vose,
                                            peliculaId = session.pelicula.id,
                                            horario = session.horario.toString()
                                        )
                                    )
                                ) {
                                    is ApiResult.Success -> {
                                        state = state.copy(
                                            sessions = state.sessions.filterNot {
                                                it.numSala == session.numSala &&
                                                    it.pelicula.id == session.pelicula.id &&
                                                    it.horario == session.horario
                                            }
                                        )
                                    }

                                    is ApiResult.Error -> {
                                        errorMessage = "Error al borrar sesión"
                                    }
                                }
                            }
                        }
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        sessionToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}