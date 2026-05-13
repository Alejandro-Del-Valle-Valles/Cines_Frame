package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import kotlinx.coroutines.launch
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.error.AppError
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorAlergenoApi
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorProductoApi
import com.alejandro.proyecto_cines_frame.data.remote.client.HttpClientFactory
import com.alejandro.proyecto_cines_frame.data.remote.dto.AlergenoDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.ProductoDTO
import com.alejandro.proyecto_cines_frame.data.repository.AlergenoRepositoryImpl
import com.alejandro.proyecto_cines_frame.data.repository.ProductoRepositoryImpl
import com.alejandro.proyecto_cines_frame.domain.model.Alergeno

@Composable
fun ManageProductsScreen(
    onBack: () -> Unit
) {
    var state by remember {
        mutableStateOf(ManageProductsUiState())
    }
    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val productoRepository = remember {
        ProductoRepositoryImpl(
            api = KtorProductoApi(HttpClientFactory.create())
        )
    }

    val alergenoRepository = remember {
        AlergenoRepositoryImpl(
            api = KtorAlergenoApi(HttpClientFactory.create())
        )
    }

    suspend fun refreshData() {
        isLoading = true

        val productosResult = productoRepository.getAll()
        val alergenosResult = alergenoRepository.getAll()

        if (productosResult is ApiResult.Success) {
            state = state.copy(products = productosResult.data)
        } else if (productosResult is ApiResult.Error) {
            state = state.copy(products = emptyList())
            snackbarHostState.showSnackbar(
                message = toManageProductsErrorMessage(productosResult.error),
                duration = SnackbarDuration.Long
            )
        }

        if (alergenosResult is ApiResult.Success) {
            state = state.copy(alergenos = alergenosResult.data)
        } else if (alergenosResult is ApiResult.Error) {
            state = state.copy(alergenos = emptyList())
            snackbarHostState.showSnackbar(
                message = toManageProductsErrorMessage(alergenosResult.error),
                duration = SnackbarDuration.Long
            )
        }

        isLoading = false
    }

    LaunchedEffect(productoRepository, alergenoRepository) {
        refreshData()
    }

    Box(modifier = Modifier.fillMaxSize().background(BackgroundDark)) {

        BackButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .zIndex(100f)
                .padding(16.dp)
        )

        ManageProducts(

            state = state,


            onTabChange = { tab ->

                state = state.copy(
                    selectedTab = tab
                )
            },

            onAddProduct = {
                state = state.copy(
                    isProductDialogVisible = true,
                    editingProduct = null,
                    productForm = ProductFormState()
                )
            },

            onEditProduct = { product ->
                state = state.copy(
                    isProductDialogVisible = true,
                    editingProduct = product,
                    productForm = ProductFormState(
                        nombre = product.nombre,
                        precio = product.precio.toString(),
                        stock = product.stock.toString(),
                        alergenosCsv =
                            ManageProductsUtils.alergenosToCsv(
                                product.alergenos
                            )
                    )
                )
            },

            onDeleteProduct = { product ->

                scope.launch {
                    val result = productoRepository.deleteProducto(product.nombre)
                    if (result is ApiResult.Success) {
                        refreshData()
                    } else if (result is ApiResult.Error) {
                        snackbarHostState.showSnackbar(
                            message = toManageProductsErrorMessage(result.error),
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            },

            onAddAlergeno = {

                state = state.copy(
                    isAlergenoDialogVisible = true,
                    editingAlergeno = null,
                    alergenoForm = AlergenoFormState()
                )
            },

            onEditAlergeno = { alergeno ->

                state = state.copy(
                    isAlergenoDialogVisible = true,

                    editingAlergeno = alergeno,

                    alergenoForm = AlergenoFormState(
                        nombre = alergeno.nombre
                    )
                )
            },

            onDeleteAlergeno = { alergeno ->

                scope.launch {
                    val result = alergenoRepository.deleteAlergeno(alergeno.nombre)
                    if (result is ApiResult.Success) {
                        refreshData()
                    } else if (result is ApiResult.Error) {
                        snackbarHostState.showSnackbar(
                            message = toManageProductsErrorMessage(result.error),
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }
        )

        if (state.isProductDialogVisible) {
            AlertDialog(
                onDismissRequest = {
                    state = state.copy(isProductDialogVisible = false)
                },
                title = {
                    Text(
                        if (state.editingProduct == null) "Nuevo producto" else "Editar producto"
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = state.productForm.nombre,
                            onValueChange = {
                                state = state.copy(
                                    productForm = state.productForm.copy(nombre = it)
                                )
                            },
                            label = { Text("Nombre") }
                        )

                        OutlinedTextField(
                            value = state.productForm.precio,
                            onValueChange = {
                                state = state.copy(
                                    productForm = state.productForm.copy(precio = it)
                                )
                            },
                            label = { Text("Precio") }
                        )

                        OutlinedTextField(
                            value = state.productForm.stock,
                            onValueChange = {
                                state = state.copy(
                                    productForm = state.productForm.copy(stock = it)
                                )
                            },
                            label = { Text("Stock") }
                        )

                        OutlinedTextField(
                            value = state.productForm.alergenosCsv,
                            onValueChange = {
                                state = state.copy(
                                    productForm = state.productForm.copy(alergenosCsv = it)
                                )
                            },
                            label = { Text("Alérgenos separados por coma") }
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val nombre = state.productForm.nombre.trim()
                            val precio = state.productForm.precio.toFloatOrNull()
                            val stock = state.productForm.stock.toIntOrNull()
                            val alergenos = parseAlergenosCsv(
                                state.productForm.alergenosCsv
                            )

                            if (nombre.isBlank() || precio == null || stock == null) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Revisa los campos",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                return@TextButton
                            }

                            val dto = ProductoDTO(
                                nombre = nombre,
                                precio = precio,
                                stock = stock,
                                alergenos = alergenos.map { AlergenoDTO(it.nombre) }
                            )

                            val antiguoNombre = state.editingProduct?.nombre
                            scope.launch {
                                val result = if (antiguoNombre == null) {
                                    productoRepository.createProducto(dto)
                                } else {
                                    productoRepository.updateProducto(antiguoNombre, dto)
                                }

                                if (result is ApiResult.Success) {
                                    state = state.copy(
                                        isProductDialogVisible = false,
                                        editingProduct = null,
                                        productForm = ProductFormState()
                                    )
                                    refreshData()
                                } else if (result is ApiResult.Error) {
                                    snackbarHostState.showSnackbar(
                                        message = toManageProductsErrorMessage(result.error),
                                        duration = SnackbarDuration.Long
                                    )
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
                            state = state.copy(isProductDialogVisible = false)
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        if (state.isAlergenoDialogVisible) {
            AlertDialog(
                onDismissRequest = {
                    state = state.copy(isAlergenoDialogVisible = false)
                },
                title = {
                    Text(
                        if (state.editingAlergeno == null) "Nuevo alérgeno" else "Editar alérgeno"
                    )
                },
                text = {
                    OutlinedTextField(
                        value = state.alergenoForm.nombre,
                        onValueChange = {
                            state = state.copy(
                                alergenoForm = state.alergenoForm.copy(nombre = it)
                            )
                        },
                        label = { Text("Nombre") }
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val nombre = state.alergenoForm.nombre.trim()
                            if (nombre.isBlank()) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Revisa los campos",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                                return@TextButton
                            }

                            val dto = AlergenoDTO(nombre = nombre)
                            val antiguoNombre = state.editingAlergeno?.nombre

                            scope.launch {
                                val result = if (antiguoNombre == null) {
                                    alergenoRepository.createAlergeno(dto)
                                } else {
                                    alergenoRepository.updateAlergeno(antiguoNombre, dto)
                                }

                                if (result is ApiResult.Success) {
                                    state = state.copy(
                                        isAlergenoDialogVisible = false,
                                        editingAlergeno = null,
                                        alergenoForm = AlergenoFormState()
                                    )
                                    refreshData()
                                } else if (result is ApiResult.Error) {
                                    snackbarHostState.showSnackbar(
                                        message = toManageProductsErrorMessage(result.error),
                                        duration = SnackbarDuration.Long
                                    )
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
                            state = state.copy(isAlergenoDialogVisible = false)
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundDark.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

private fun parseAlergenosCsv(csv: String): Set<Alergeno> {
    return csv
        .split(",")
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .map { Alergeno(it) }
        .toSet()
}

private fun toManageProductsErrorMessage(error: AppError): String {
    return when (error) {
        is AppError.Network -> "Error de red. Comprueba tu conexión."
        is AppError.Server -> "Error del servidor. Inténtalo más tarde."
        is AppError.Unauthorized -> "No autorizado."
        else -> "Ha ocurrido un error inesperado."
    }
}