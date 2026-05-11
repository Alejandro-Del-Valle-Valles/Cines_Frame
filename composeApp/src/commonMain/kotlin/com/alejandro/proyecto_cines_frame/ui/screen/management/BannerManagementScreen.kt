package com.alejandro.proyecto_cines_frame.ui.screen.management

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.error.AppError
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorBanerApi
import com.alejandro.proyecto_cines_frame.data.remote.client.HttpClientFactory
import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO
import com.alejandro.proyecto_cines_frame.data.repository.BanerRepositoryImpl
import com.alejandro.proyecto_cines_frame.domain.extension.toFirstUiMessagePerField
import com.alejandro.proyecto_cines_frame.domain.model.Baner
import com.alejandro.proyecto_cines_frame.domain.model.input.BanerCreateInput
import com.alejandro.proyecto_cines_frame.domain.repository.BanerRepository
import com.alejandro.proyecto_cines_frame.domain.validation.BanerCreateValidator
import com.alejandro.proyecto_cines_frame.domain.validation.FieldErrors
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner.BannerFormState
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner.BannerUiState
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner.ManageBanner
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageBannerScreen(
    onBack: () -> Unit,
    bannerRepository: BanerRepository? = null
) {

    val repository = bannerRepository ?: remember {
        BanerRepositoryImpl(
            api = KtorBanerApi(HttpClientFactory.create())
        )
    }

    var state by remember {
        mutableStateOf(BannerUiState())
    }

    var formErrors by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var bannerToDelete by remember { mutableStateOf<BanerDTO?>(null) }
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun mapBanner(banner: Baner): BanerDTO {
        return BanerDTO(
            id = banner.id,
            peliculaId = banner.peliculaId,
            url = banner.url,
            empieza = banner.empeiza.toString(),
            termina = banner.termina.toString()
        )
    }

    suspend fun fetchBanners() {
        when (val result = repository.getAll()) {
            is ApiResult.Success -> {
                state = state.copy(banners = result.data.map(::mapBanner))
            }

            is ApiResult.Error -> {
                snackbarHostState.showSnackbar(
                    message = toBannerErrorMessage(result.error),
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchBanners()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            BackButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .zIndex(100f)
                    .padding(16.dp)
            )

            ManageBanner(

                state = state,

                onAddBanner = {

                    state = state.copy(
                        isDialogVisible = true,
                        editingBanner = null,
                        form = BannerFormState()
                    )
                    formErrors = emptyMap()
                },

                onEditBanner = { banner ->

                    state = state.copy(
                        isDialogVisible = true,

                        editingBanner = banner,

                        form = BannerFormState(
                            peliculaId = banner.peliculaId,
                            imageUrl = banner.url,
                            fechaInicio = banner.empieza,
                            fechaFin = banner.termina
                        )
                    )
                    formErrors = emptyMap()
                },

                onDeleteBanner = { banner ->
                    bannerToDelete = banner
                }
            )
        }
    }

    if (bannerToDelete != null) {
        AlertDialog(
            onDismissRequest = { bannerToDelete = null },
            title = { Text("Eliminar banner") },
            text = {
                Text("¿Seguro que deseas eliminar este banner?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val banner = bannerToDelete
                        bannerToDelete = null
                        if (banner != null && banner.id != null) {
                            scope.launch {
                                when (val result = repository.deleteBaner(banner.id)) {
                                    is ApiResult.Success -> {
                                        println("Banner eliminado: ${result.data.url}")
                                        fetchBanners()
                                        snackbarHostState.showSnackbar(
                                            message = "Banner eliminado correctamente",
                                            duration = SnackbarDuration.Short
                                        )
                                    }

                                    is ApiResult.Error -> {
                                        println(result.error)
                                        snackbarHostState.showSnackbar(
                                            message = toBannerErrorMessage(result.error),
                                            duration = SnackbarDuration.Long
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
                    onClick = { bannerToDelete = null }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (state.isDialogVisible) {
        val startPickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.form.fechaInicio
                .takeIf { it.isNotBlank() }
                ?.let { LocalDate.parse(it).atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds() }
        )
        val endPickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.form.fechaFin
                .takeIf { it.isNotBlank() }
                ?.let { LocalDate.parse(it).atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds() }
        )

        AlertDialog(

            onDismissRequest = {

                state = state.copy(
                    isDialogVisible = false
                )
                formErrors = emptyMap()
            },

            title = {

                Text(
                    if (state.editingBanner == null)
                        "Nuevo banner"
                    else
                        "Editar banner"
                )
            },

            text = {

                Column {

                    OutlinedTextField(

                        value = state.form.peliculaId,

                        onValueChange = {

                            state = state.copy(
                                form = state.form.copy(
                                    peliculaId = it
                                )
                            )
                        },

                        label = {
                            Text("Película ID")
                        }
                    )

                    formErrors["peliculaId"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(

                        value = state.form.imageUrl,

                        onValueChange = {

                            state = state.copy(
                                form = state.form.copy(
                                    imageUrl = it
                                )
                            )
                        },

                        label = {
                            Text("URL imagen")
                        }
                    )

                    formErrors["url"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value = state.form.fechaInicio,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Fecha inicio") },
                        trailingIcon = {
                            TextButton(onClick = { showStartPicker = true }) {
                                Text("Elegir")
                            }
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value = state.form.fechaFin,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Fecha fin") },
                        trailingIcon = {
                            TextButton(onClick = { showEndPicker = true }) {
                                Text("Elegir")
                            }
                        }
                    )

                    formErrors["fechas"]?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            },

            confirmButton = {

                TextButton(

                    onClick = {
                        val oldUrl = state.editingBanner?.url
                        if (state.editingBanner != null && oldUrl.isNullOrBlank()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "No se pudo obtener la URL original del banner",
                                    duration = SnackbarDuration.Short
                                )
                            }
                            return@TextButton
                        }

                        val input = runCatching {
                            BanerCreateInput(
                                id = state.editingBanner?.id,
                                peliculaId = state.form.peliculaId,
                                antiguaUrl = oldUrl,
                                url = state.form.imageUrl,
                                empieza = LocalDate.parse(state.form.fechaInicio),
                                termina = LocalDate.parse(state.form.fechaFin)
                            )
                        }.getOrNull()

                        if (input == null) {
                            formErrors = mapOf("fechas" to "Formato inválido. Se espera: YYYY-MM-DD")
                            return@TextButton
                        }

                        val errors: FieldErrors = BanerCreateValidator.validateCreate(input)
                        val uiErrors = errors.toFirstUiMessagePerField()

                        if (uiErrors.isNotEmpty()) {
                            formErrors = uiErrors
                            return@TextButton
                        }

                        formErrors = emptyMap()

                        val isEditing = state.editingBanner != null
                        state = state.copy(isDialogVisible = false)

                        scope.launch {
                            val dto = BanerDTO(
                                id = state.editingBanner?.id ?: 0,
                                peliculaId = input.peliculaId,
                                url = input.url,
                                empieza = input.empieza.toString(),
                                termina = input.termina.toString()
                            )

                            val result = if (!isEditing) {
                                repository.createBaner(dto)
                            } else {
                                repository.updateBaner(dto)
                            }

                            when (result) {
                                is ApiResult.Success -> {
                                    println("Banner creado: ${result.data.url}")
                                    fetchBanners()
                                    snackbarHostState.showSnackbar(
                                        message = if (state.editingBanner == null) {
                                            "Banner creado correctamente"
                                        } else {
                                            "Banner actualizado correctamente"
                                        },
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                is ApiResult.Error -> {
                                    println(result.error)
                                    snackbarHostState.showSnackbar(
                                        message = toBannerErrorMessage(result.error),
                                        duration = SnackbarDuration.Long
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
                        state = state.copy(
                            isDialogVisible = false
                        )
                        formErrors = emptyMap()
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )

        if (showStartPicker) {
            DatePickerDialog(
                onDismissRequest = { showStartPicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val millis = startPickerState.selectedDateMillis
                            if (millis != null) {
                                val date = Instant
                                    .fromEpochMilliseconds(millis)
                                    .toLocalDateTime(TimeZone.currentSystemDefault())
                                    .date
                                state = state.copy(form = state.form.copy(fechaInicio = date.toString()))
                            }
                            showStartPicker = false
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showStartPicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(state = startPickerState)
            }
        }

        if (showEndPicker) {
            DatePickerDialog(
                onDismissRequest = { showEndPicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val millis = endPickerState.selectedDateMillis
                            if (millis != null) {
                                val date = Instant
                                    .fromEpochMilliseconds(millis)
                                    .toLocalDateTime(TimeZone.currentSystemDefault())
                                    .date
                                state = state.copy(form = state.form.copy(fechaFin = date.toString()))
                            }
                            showEndPicker = false
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEndPicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(state = endPickerState)
            }
        }
    }
}

private fun toBannerErrorMessage(error: AppError): String {
    return when (error) {
        is AppError.Network -> "Error de red. Comprueba tu conexión."
        is AppError.Server -> "Error del servidor. Inténtalo más tarde."
        is AppError.Unauthorized -> "No autorizado."
        else -> "Ha ocurrido un error inesperado."
    }
}