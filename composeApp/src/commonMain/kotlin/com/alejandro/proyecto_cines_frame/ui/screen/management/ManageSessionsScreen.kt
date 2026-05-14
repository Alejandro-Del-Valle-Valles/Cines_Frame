package com.alejandro.proyecto_cines_frame.ui.screen.management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import com.alejandro.proyecto_cines_frame.domain.model.input.SesionCreateInput
import com.alejandro.proyecto_cines_frame.domain.repository.SesionRepository
import com.alejandro.proyecto_cines_frame.domain.validation.FieldError
import com.alejandro.proyecto_cines_frame.domain.validation.SesionValidator
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.*
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    var isCreateDialogVisible by remember { mutableStateOf(false) }
    var form by remember { mutableStateOf(SessionFormState()) }
    var formErrors by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val snackbarHostState = remember { SnackbarHostState() }

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

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(paddingValues)
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
                onAddSession = {
                    form = SessionFormState()
                    formErrors = emptyMap()
                    isCreateDialogVisible = true
                },
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
                                            snackbarHostState.showSnackbar(
                                                message = "Sesión eliminada correctamente",
                                                duration = SnackbarDuration.Short
                                            )
                                        }

                                        is ApiResult.Error -> {
                                            snackbarHostState.showSnackbar(
                                                message = "Error inesperado al eliminar la sesión",
                                                duration = SnackbarDuration.Short
                                            )
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

        if (isCreateDialogVisible) {
            AlertDialog(
                onDismissRequest = {
                    isCreateDialogVisible = false
                    formErrors = emptyMap()
                },
                title = {
                    Text("Nueva sesion")
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = form.peliculaId,
                            onValueChange = {
                                form = form.copy(peliculaId = it)
                                formErrors = formErrors - "peliculaId"
                            },
                            label = { Text("Pelicula ID") },
                            isError = formErrors.containsKey("peliculaId")
                        )

                        formErrors["peliculaId"]?.let { Text(it, color = TextWhite) }

                        OutlinedTextField(
                            value = form.numSala,
                            onValueChange = {
                                form = form.copy(numSala = it)
                                formErrors = formErrors - "salaId"
                            },
                            label = { Text("Sala") },
                            isError = formErrors.containsKey("salaId")
                        )

                        formErrors["salaId"]?.let { Text(it, color = TextWhite) }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = form.fecha,
                                onValueChange = {},
                                label = { Text("Fecha") },
                                readOnly = true,
                                modifier = Modifier.weight(1f),
                                isError = formErrors.containsKey("horario")
                            )

                            Spacer(Modifier.width(8.dp))

                            TextButton(onClick = { showDatePicker = true }) {
                                Text("Elegir")
                            }
                        }

                        OutlinedTextField(
                            value = form.hora,
                            onValueChange = {
                                form = form.copy(hora = it)
                                formErrors = formErrors - "horario"
                            },
                            label = { Text("Hora (HH:mm)") },
                            isError = formErrors.containsKey("horario")
                        )

                        formErrors["horario"]?.let { Text(it, color = TextWhite) }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            androidx.compose.material3.Checkbox(
                                checked = form.tresD,
                                onCheckedChange = { form = form.copy(tresD = it) }
                            )
                            Text("3D")

                            Spacer(Modifier.width(16.dp))

                            androidx.compose.material3.Checkbox(
                                checked = form.vose,
                                onCheckedChange = { form = form.copy(vose = it) }
                            )
                            Text("VOSE")
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val sala = form.numSala.toIntOrNull() ?: -1
                            val horario = ManageSessionsUtils.parseFormDateTime(
                                fecha = form.fecha,
                                hora = form.hora
                            )

                            if (horario == null) {
                                formErrors = mapOf("horario" to "Horario invalido")
                                return@TextButton
                            }

                            val input = SesionCreateInput(
                                numSala = sala,
                                tresD = form.tresD,
                                vose = form.vose,
                                peliculaId = form.peliculaId.trim(),
                                horario = horario
                            )

                            val validationErrors = SesionValidator.validateCreate(input)
                                .mapValues { entry -> fieldErrorToMessage(entry.value.first()) }
                                .toMutableMap()


                            if (validationErrors.isNotEmpty()) {
                                formErrors = validationErrors
                                return@TextButton
                            }

                            scope.launch {
                                when (
                                    val result = repository.createSesion(
                                        SesionCrudDTO(
                                            numSala = input.numSala,
                                            tresD = input.tresD,
                                            vose = input.vose,
                                            peliculaId = input.peliculaId,
                                            horario = input.horario.toString()
                                        )
                                    )
                                ) {
                                    is ApiResult.Success -> {
                                        state = state.copy(
                                            sessions = state.sessions + result.data
                                        )
                                        isCreateDialogVisible = false
                                        snackbarHostState.showSnackbar(
                                            message = "Sesion creada",
                                            duration = SnackbarDuration.Short
                                        )
                                    }

                                    is ApiResult.Error -> {
                                        snackbarHostState.showSnackbar(
                                            message = "Error al crear sesion",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            }
                        }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            isCreateDialogVisible = false
                            formErrors = emptyMap()
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val date = Instant
                                .fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                                .date
                            form = form.copy(fecha = date.toString())
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

private fun fieldErrorToMessage(error: FieldError): String {
    return when (error) {
        is FieldError.Custom -> error.message
        is FieldError.InvalidFormat -> "Formato invalido"
        is FieldError.Required -> error.fieldName
        is FieldError.TooLong -> "Muy largo"
        is FieldError.TooShort -> "Muy corto"
    }
}