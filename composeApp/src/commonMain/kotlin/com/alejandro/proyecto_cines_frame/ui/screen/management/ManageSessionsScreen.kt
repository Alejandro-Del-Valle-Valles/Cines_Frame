package com.alejandro.proyecto_cines_frame.ui.screen.management

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageSessions.*
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark

@Composable
fun ManageSessionsScreen(
    onBack: () -> Unit
) {

    var state by remember {

        mutableStateOf(

            SessionUiState(

                sessions =
                    ManageSessionsUtils.sampleSessions()
            )
        )
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

            onAddSession = {

                state = state.copy(

                    isDialogVisible = true,

                    editingSession = null,

                    errors = SessionFormErrors(),

                    form = SessionFormState(

                        peliculaId = "",

                        peliculaNombre = "",

                        numSala = "",

                        fecha = "2026-05-13",

                        hora = "17:45",

                        tresD = false,

                        vose = false
                    )
                )
            },

            onEditSession = { session ->

                state = state.copy(

                    isDialogVisible = true,

                    editingSession = session,

                    errors = SessionFormErrors(),

                    form =
                        ManageSessionsUtils.run {
                            session.toFormState()
                        }
                )
            },

            onDeleteSession = { session ->

                state = state.copy(

                    sessions = state.sessions.filterNot {

                        it.peliculaId == session.peliculaId &&
                                it.numSala == session.numSala &&
                                it.horario == session.horario
                    }
                )
            }
        )
    }

    if (state.isDialogVisible) {

        AlertDialog(

            onDismissRequest = {

                state = state.copy(
                    isDialogVisible = false
                )
            },

            title = {

                Text(

                    if (state.editingSession == null)
                        "Nueva sesión"
                    else
                        "Editar sesión"
                )
            },

            text = {

                val scrollState =
                    rememberScrollState()

                Box {

                    Column(

                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 500.dp)
                            .verticalScroll(scrollState)
                            .padding(end = 12.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        OutlinedTextField(

                            value = state.form.peliculaId,

                            onValueChange = {

                                state = state.copy(

                                    form = state.form.copy(
                                        peliculaId = it
                                    ),

                                    errors = state.errors.copy(
                                        peliculaId = null
                                    )
                                )
                            },

                            label = {
                                Text("Película ID")
                            },

                            isError =
                                state.errors.peliculaId != null,

                            supportingText = {

                                state.errors.peliculaId?.let {
                                    Text(it)
                                }
                            }
                        )

                        OutlinedTextField(

                            value = state.form.peliculaNombre,

                            onValueChange = {

                                state = state.copy(

                                    form = state.form.copy(
                                        peliculaNombre = it
                                    ),

                                    errors = state.errors.copy(
                                        peliculaNombre = null
                                    )
                                )
                            },

                            label = {
                                Text("Nombre de la película")
                            },

                            isError =
                                state.errors.peliculaNombre != null,

                            supportingText = {

                                state.errors.peliculaNombre?.let {
                                    Text(it)
                                }
                            }
                        )

                        OutlinedTextField(

                            value = state.form.numSala,

                            onValueChange = {

                                state = state.copy(

                                    form = state.form.copy(
                                        numSala = it
                                    ),

                                    errors = state.errors.copy(
                                        numSala = null
                                    )
                                )
                            },

                            label = {
                                Text("Sala")
                            },

                            isError =
                                state.errors.numSala != null,

                            supportingText = {

                                state.errors.numSala?.let {
                                    Text(it)
                                }
                            }
                        )

                        OutlinedTextField(

                            value = state.form.fecha,

                            onValueChange = {

                                state = state.copy(

                                    form = state.form.copy(
                                        fecha = it
                                    ),

                                    errors = state.errors.copy(
                                        fecha = null
                                    )
                                )
                            },

                            label = {
                                Text("Fecha (YYYY-MM-DD)")
                            },

                            isError =
                                state.errors.fecha != null,

                            supportingText = {

                                state.errors.fecha?.let {
                                    Text(it)
                                }
                            }
                        )

                        OutlinedTextField(

                            value = state.form.hora,

                            onValueChange = {

                                state = state.copy(

                                    form = state.form.copy(
                                        hora = it
                                    ),

                                    errors = state.errors.copy(
                                        hora = null
                                    )
                                )
                            },

                            label = {
                                Text("Hora (HH:mm)")
                            },

                            isError =
                                state.errors.hora != null,

                            supportingText = {

                                state.errors.hora?.let {
                                    Text(it)
                                }
                            }
                        )

                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Checkbox(

                                checked = state.form.tresD,

                                onCheckedChange = {

                                    state = state.copy(

                                        form = state.form.copy(
                                            tresD = it
                                        )
                                    )
                                }
                            )

                            Text("3D")
                        }

                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Checkbox(

                                checked = state.form.vose,

                                onCheckedChange = {

                                    state = state.copy(

                                        form = state.form.copy(
                                            vose = it
                                        )
                                    )
                                }
                            )

                            Text("VOSE")
                        }

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )
                    }

                    VerticalScrollbar(

                        adapter =
                            rememberScrollbarAdapter(
                                scrollState
                            ),

                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight()
                    )
                }
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        val errors = SessionFormErrors(

                            peliculaId =
                                if (state.form.peliculaId.isBlank())
                                    "Campo obligatorio"
                                else
                                    null,

                            peliculaNombre =
                                if (state.form.peliculaNombre.isBlank())
                                    "Campo obligatorio"
                                else
                                    null,

                            numSala =
                                if (state.form.numSala.toIntOrNull() == null)
                                    "Sala inválida"
                                else
                                    null,

                            fecha =
                                if (
                                    !state.form.fecha.matches(
                                        Regex("\\d{4}-\\d{2}-\\d{2}")
                                    )
                                )
                                    "Formato inválido"
                                else
                                    null,

                            hora =
                                if (
                                    !state.form.hora.matches(
                                        Regex("\\d{2}:\\d{2}")
                                    )
                                )
                                    "Formato inválido"
                                else
                                    null
                        )

                        if (

                            listOf(

                                errors.peliculaId,
                                errors.peliculaNombre,
                                errors.numSala,
                                errors.fecha,
                                errors.hora

                            ).any { it != null }

                        ) {

                            state = state.copy(
                                errors = errors
                            )

                            return@TextButton
                        }

                        val sala =
                            state.form.numSala.toInt()

                        val horario =
                            ManageSessionsUtils.parseFormDateTime(

                                fecha = state.form.fecha,

                                hora = state.form.hora
                            ) ?: return@TextButton

                        val newSession = SessionUiModel(

                            peliculaId =
                                state.form.peliculaId.trim(),

                            peliculaNombre =
                                state.form.peliculaNombre.trim(),

                            numSala = sala,

                            horario = horario,

                            tresD =
                                state.form.tresD,

                            vose =
                                state.form.vose
                        )

                        state =
                            if (state.editingSession == null) {

                                state.copy(

                                    sessions =
                                        state.sessions + newSession,

                                    isDialogVisible = false
                                )

                            } else {

                                state.copy(

                                    sessions =
                                        state.sessions.map {

                                            if (

                                                it.peliculaId ==
                                                state.editingSession?.peliculaId &&

                                                it.numSala ==
                                                state.editingSession?.numSala &&

                                                it.horario ==
                                                state.editingSession?.horario

                                            )

                                                newSession

                                            else
                                                it
                                        },

                                    isDialogVisible = false
                                )
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
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }
}