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

@Composable
fun ManageProductsScreen(
    onBack: () -> Unit
) {
    var state by remember {
        mutableStateOf(
            ManageProductsUiState(
                products = listOf(
                    ProductUiModel(
                        nombre = "Combo Palomitas Grande",
                        precio = 15.00f,
                        stock = 120,
                        alergenos = listOf("Gluten", "Leche"),
                        descripcion = "Palomitas grandes + bebida"
                    ),
                    ProductUiModel(
                        nombre = "Gaseosa 1.5L",
                        precio = 7.00f,
                        stock = 85,
                        alergenos = emptyList(),
                        descripcion = "Gaseosa de cola 1.5 litros"
                    ),
                    ProductUiModel(
                        nombre = "Nachos con Queso",
                        precio = 8.00f,
                        stock = 60,
                        alergenos = listOf("Leche"),
                        descripcion = "Nachos con salsa de queso"
                    ),
                    ProductUiModel(
                        nombre = "Agua Mineral",
                        precio = 4.00f,
                        stock = 200,
                        alergenos = emptyList(),
                        descripcion = "Agua mineral sin gas"
                    )
                ),
                alergenos = listOf(
                    AlergenoUiModel("Gluten"),
                    AlergenoUiModel("Leche"),
                    AlergenoUiModel("Huevos"),
                    AlergenoUiModel("Maní"),
                    AlergenoUiModel("Soya"),
                    AlergenoUiModel("Frutos secos")
                )
            )
        )
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

            onBack = onBack,

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
                            ),
                        descripcion = product.descripcion
                    )
                )
            },

            onDeleteProduct = { product ->

                state = state.copy(
                    products =
                        state.products.filterNot {
                            it.nombre == product.nombre
                        }
                )
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

                state = state.copy(

                    alergenos =
                        state.alergenos.filterNot {
                            it.nombre == alergeno.nombre
                        },

                    products =
                        state.products.map { product ->

                            product.copy(

                                alergenos =
                                    product.alergenos.filterNot {
                                        it == alergeno.nombre
                                    }
                            )
                        }
                )
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

                        OutlinedTextField(
                            value = state.productForm.descripcion,
                            onValueChange = {
                                state = state.copy(
                                    productForm = state.productForm.copy(descripcion = it)
                                )
                            },
                            label = { Text("Descripción") }
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val precio = state.productForm.precio.toFloatOrNull() ?: 0f
                            val stock = state.productForm.stock.toIntOrNull() ?: 0
                            val alergenos = state.productForm.alergenosCsv
                                .split(",")
                                .map { it.trim() }
                                .filter { it.isNotBlank() }

                            val nuevo = ProductUiModel(
                                nombre = state.productForm.nombre.trim(),
                                precio = precio,
                                stock = stock,
                                alergenos = alergenos,
                                descripcion = state.productForm.descripcion.trim()
                            )

                            state = if (state.editingProduct == null) {
                                state.copy(
                                    products = state.products + nuevo,
                                    isProductDialogVisible = false
                                )
                            } else {
                                state.copy(
                                    products = state.products.map {
                                        if (it.nombre == state.editingProduct?.nombre) nuevo else it
                                    },
                                    isProductDialogVisible = false
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
                            val nuevo = AlergenoUiModel(state.alergenoForm.nombre.trim())

                            state = if (state.editingAlergeno == null) {
                                state.copy(
                                    alergenos = state.alergenos + nuevo,
                                    isAlergenoDialogVisible = false
                                )
                            } else {
                                state.copy(
                                    alergenos = state.alergenos.map {
                                        if (it.nombre == state.editingAlergeno?.nombre) nuevo else it
                                    },
                                    isAlergenoDialogVisible = false
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
                            state = state.copy(isAlergenoDialogVisible = false)
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}