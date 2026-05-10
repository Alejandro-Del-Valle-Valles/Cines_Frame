package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton

@Composable
fun ManageBannerScreen(
    onBack: () -> Unit
) {

    var state by remember {

        mutableStateOf(
            BannerUiState(
                banners = listOf(

                    BannerUiModel(
                        peliculaId = "1",
                        titulo = "Dune Parte Dos",
                        imageUrl = "https://picsum.photos/500/200",
                        fechaInicio = "01/03/2024",
                        fechaFin = "31/03/2024",
                        activo = true
                    ),

                    BannerUiModel(
                        peliculaId = "2",
                        titulo = "Godzilla y Kong",
                        imageUrl = "https://picsum.photos/500/201",
                        fechaInicio = "15/03/2024",
                        fechaFin = "14/04/2024",
                        activo = false
                    )
                )
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            onClick = onBack,

            modifier = Modifier
                .align(Alignment.TopStart)
                .zIndex(100f)
                .padding(16.dp)
        ) {
            Text("← Volver")
        }
        ManageBanner(

            state = state,

            onAddBanner = {

                state = state.copy(
                    isDialogVisible = true,
                    editingBanner = null,
                    form = BannerFormState()
                )
            },

            onEditBanner = { banner ->

                state = state.copy(
                    isDialogVisible = true,

                    editingBanner = banner,

                    form = BannerFormState(
                        peliculaId = banner.peliculaId,
                        imageUrl = banner.imageUrl,
                        fechaInicio = banner.fechaInicio,
                        fechaFin = banner.fechaFin
                    )
                )
            },

            onDeleteBanner = { banner ->

                state = state.copy(
                    banners =
                        state.banners.filterNot {
                            it.peliculaId == banner.peliculaId
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

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(

                        value = state.form.fechaInicio,

                        onValueChange = {

                            state = state.copy(
                                form = state.form.copy(
                                    fechaInicio = it
                                )
                            )
                        },

                        label = {
                            Text("Fecha inicio")
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(

                        value = state.form.fechaFin,

                        onValueChange = {

                            state = state.copy(
                                form = state.form.copy(
                                    fechaFin = it
                                )
                            )
                        },

                        label = {
                            Text("Fecha fin")
                        }
                    )
                }
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        val bannerNuevo = BannerUiModel(

                            peliculaId =
                                state.form.peliculaId,

                            titulo =
                                "Película ${state.form.peliculaId}",

                            imageUrl =
                                state.form.imageUrl,

                            fechaInicio =
                                state.form.fechaInicio,

                            fechaFin =
                                state.form.fechaFin,

                            activo = true
                        )

                        state =
                            if (state.editingBanner == null) {

                                state.copy(
                                    banners =
                                        state.banners + bannerNuevo,

                                    isDialogVisible = false
                                )

                            } else {

                                state.copy(

                                    banners =
                                        state.banners.map {

                                            if (
                                                it.peliculaId ==
                                                state.editingBanner?.peliculaId
                                            ) {
                                                bannerNuevo
                                            } else {
                                                it
                                            }
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