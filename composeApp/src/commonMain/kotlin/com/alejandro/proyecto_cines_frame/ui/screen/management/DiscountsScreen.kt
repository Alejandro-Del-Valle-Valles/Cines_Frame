package com.alejandro.proyecto_cines_frame.ui.screen.management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorCodigoDescuentoApi
import com.alejandro.proyecto_cines_frame.data.remote.client.HttpClientFactory
import com.alejandro.proyecto_cines_frame.data.remote.dto.CodigoDescuentoCreateDTO
import com.alejandro.proyecto_cines_frame.data.repository.CodigoDescuentoRepositoryImpl
import com.alejandro.proyecto_cines_frame.domain.enums.DescuentoCondicion
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento
import com.alejandro.proyecto_cines_frame.ui.components.admin.DiscountsScreens.DiscountsScreenDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.DiscountsScreens.DiscountsScreenMobile
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import kotlinx.coroutines.launch

@Composable
fun DiscountsScreen (
    //discounts: List<CodigoDescuento>,
    //onEditDiscount: (DiscountCode) -> Unit
    //onEditDiscount: () -> Unit,
    onBack: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var discountToEdit by remember {
        mutableStateOf<CodigoDescuento?>(null)
    }
    val scope = rememberCoroutineScope()

    val codigoDescuentoRepo = remember {
        CodigoDescuentoRepositoryImpl(
            api = KtorCodigoDescuentoApi(HttpClientFactory.create())
        )
    }

    var discounts by remember {
        mutableStateOf<List<CodigoDescuento>>(emptyList())
    }

    var loadingDiscounts by remember {
        mutableStateOf(true)
    }
    if (showAddDialog) {
        AddDiscountDialog(
            onDismiss = { showAddDialog = false },
            onCreated = {

                scope.launch {

                    when (val result = codigoDescuentoRepo.getAll()) {
                        is ApiResult.Success -> {
                            discounts = result.data
                        }

                        is ApiResult.Error -> {
                            discounts = emptyList()
                        }
                    }
                }
            }
        )
    }

    discountToEdit?.let { discount ->

        EditDiscountDialog(
            discount = discount,
            onDismiss = {
                discountToEdit = null
            }
        )
    }

    LaunchedEffect(Unit) {
        when (val result = codigoDescuentoRepo.getAll()) {
            is ApiResult.Success -> {
                discounts = result.data
            }

            is ApiResult.Error -> {
                discounts = emptyList()
            }
        }

        loadingDiscounts = false
    }

    BoxWithConstraints (
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorFondoHeader)
            .padding(16.dp)
    ) {

        val esEscritorio = FooterUtils.esEscritorio(maxWidth)

        Box {

            BackButton(
                onClick = onBack,

                modifier = Modifier
                    .align(Alignment.TopStart)
                    .zIndex(10f)

                    // más abajo y más cómodo en móvil
                    .padding(

                        start = 16.dp,

                        top =
                            if (esEscritorio)
                                16.dp
                            else
                                32.dp
                    )
            )

            BoxWithConstraints(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorFondoHeader)

                    // deja espacio al botón en móvil
                    .padding(

                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,

                        top =
                            if (esEscritorio)
                                16.dp
                            else
                                90.dp
                    )
            ) {
                if (esEscritorio) {
                    DiscountsScreenDesktop(
                        discounts = discounts,
                        onAddDiscount = { showAddDialog = true },
                        onEditDiscount = { discount ->
                            discountToEdit = discount
                        },
                        onBackClick = onBack
                    )
                } else {
                    DiscountsScreenMobile(
                        discounts,
                        onAddDiscount = { showAddDialog = true },
                        onEditDiscount = { discount ->
                            discountToEdit = discount
                        },
                        onBack
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun PreviewDiscountsScreen() {
    MaterialTheme() {

        val discounts = listOf(
            CodigoDescuento(1,"DESCUENTO10", DescuentoCondicion.TODOS, 10, true),
            CodigoDescuento(2,"PELICULA05", DescuentoCondicion.TODOS, 5, true),
            CodigoDescuento(3,"ENEROLOCO", DescuentoCondicion.TODOS, 15, false),
            CodigoDescuento(4,"COMIDA5", DescuentoCondicion.TODOS, 5, true),
            CodigoDescuento(5,"DESCUENTO20", DescuentoCondicion.TODOS, 20, false)
        )

        DiscountsScreen(
            //discounts = discounts,
            //onAddDiscount = {},
            //onEditDiscount = {},
            onBack = {}
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddDiscountDialog(
    onDismiss: () -> Unit,
    onCreated: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val repository = remember {
        CodigoDescuentoRepositoryImpl(
            api = KtorCodigoDescuentoApi(HttpClientFactory.create())
        )
    }

    var codigo by remember { mutableStateOf("") }
    var porcentaje by remember { mutableStateOf("") }
    var activo by remember { mutableStateOf(true) }

    var condicion by remember {
        mutableStateOf(DescuentoCondicion.PELICULA)
    }

    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Añadir código de descuento")
        },
        text = {
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = codigo,
                    onValueChange = { codigo = it },
                    label = { Text("Código") }
                )

                Text("Condición")

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {

                    OutlinedTextField(
                        value = condicion.name,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        label = {
                            Text("Condición")
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {

                        DescuentoCondicion.entries.forEach { opcion ->

                            DropdownMenuItem(
                                text = {
                                    Text(opcion.name)
                                },
                                onClick = {
                                    condicion = opcion
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = porcentaje,
                    onValueChange = { nuevoValor ->
                        if (nuevoValor.all { it.isDigit() }) {
                            porcentaje = nuevoValor
                        }
                    },
                    label = { Text("Porcentaje") }
                )

                Row {
                    Checkbox(
                        checked = activo,
                        onCheckedChange = {
                            activo = it
                        }
                    )

                    Text("Activo")
                }
            }
        },
        confirmButton = {

            Button(
                onClick = {

                    scope.launch {

                        val dto = CodigoDescuentoCreateDTO(
                            codigo = codigo,
                            condicion = condicion,
                            porcentajeDescuento = porcentaje.toIntOrNull() ?: 0,
                            activo = activo
                        )

                        when (
                            repository.createCodigoDescuento(dto)
                        ) {

                            is ApiResult.Success -> {
                                onCreated()
                                onDismiss()
                            }

                            is ApiResult.Error -> {
                                println("Error creando descuento")
                            }
                        }
                    }
                }
            ) {
                Text("Crear código promocional")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditDiscountDialog(
    discount: CodigoDescuento,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val repository = remember {
        CodigoDescuentoRepositoryImpl(
            api = KtorCodigoDescuentoApi(HttpClientFactory.create())
        )
    }

    var codigo by remember {
        mutableStateOf(discount.codigo)
    }

    var porcentaje by remember {
        mutableStateOf(discount.porcentajeDescuento.toString())
    }

    var activo by remember {
        mutableStateOf(discount.activo)
    }

    var condicion by remember {
        mutableStateOf(discount.condicion)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text("Editar código de descuento")
        },

        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = codigo,
                    onValueChange = {
                        codigo = it
                    },
                    label = {
                        Text("Código")
                    }
                )

                Text("Condición")

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {

                    OutlinedTextField(
                        value = condicion.name,
                        onValueChange = {},
                        readOnly = true
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {

                        DescuentoCondicion.entries.forEach {

                            DropdownMenuItem(
                                text = {
                                    Text(it.name)
                                },
                                onClick = {
                                    condicion = it
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = porcentaje,
                    onValueChange = { nuevoValor ->
                        if (nuevoValor.all { it.isDigit() }) {
                            porcentaje = nuevoValor
                        }
                    },
                    label = {
                        Text("Porcentaje")
                    }
                )

                Row {

                    Checkbox(
                        checked = activo,
                        onCheckedChange = {
                            activo = it
                        }
                    )

                    Text("Activo")
                }
            }
        },

        confirmButton = {

            Button(
                onClick = {

                    scope.launch {

                        val dto = CodigoDescuentoCreateDTO(
                            codigo = codigo,
                            condicion = condicion,
                            porcentajeDescuento = porcentaje.toIntOrNull() ?: 0,
                            activo = activo
                        )

                        when (
                            repository.updateCodigoDescuento(
                                discount.id,
                                dto
                            )
                        ) {

                            is ApiResult.Success -> {
                                onDismiss()
                            }

                            is ApiResult.Error -> {
                                println("Error actualizando descuento")
                            }
                        }
                    }
                }
            ) {
                Text("Guardar cambios")
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}