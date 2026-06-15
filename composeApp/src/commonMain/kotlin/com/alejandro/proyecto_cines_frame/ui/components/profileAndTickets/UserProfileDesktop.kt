package com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.alejandro.proyecto_cines_frame.domain.model.Compra
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraEntrada
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraProducto
import com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets.CompraQrCard
import com.alejandro.proyecto_cines_frame.ui.components.common.HeaderBrand
import com.alejandro.proyecto_cines_frame.ui.logic.presenter.ProfilePresenter
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun UserProfileDesktop(
    compras: List<Compra>,
    movieTitlesById: Map<String, String>,
    errorMessage: String?,
    presenter: ProfilePresenter,
    onBackClick: () -> Unit
) {

    val state = presenter.state.collectAsState().value
    val background = BackgroundDark
    val panelColor = ColorFondoHeader
    var showEditDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf(false) }
    var showPasswordConfirmDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {

        HeaderBrand(true)

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // 👤 PANEL IZQUIERDO (USUARIO)
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .background(panelColor, RoundedCornerShape(8.dp))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(containerColor = OtroRojo),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver al menu", color = TextWhite)
                }

                Text(
                    "¡Hola ${state.currentName}!",
                    color = TextWhite,
                    style = MaterialTheme.typography.titleLarge
                )

                Text("Mi cuenta", color = TextWhite)

                Column {
                    Text("Nombre", color = TextWhite)
                    Text(state.currentName, color = TextWhite)
                }

                Spacer(modifier = Modifier.height(8.dp))

                //Botón para cambiar el nombre

                Button(
                    onClick = { showEditDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = OtroRojo),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cambiar Nombre", color = TextWhite)
                }
                if (showEditDialog) {
                    AlertDialog(
                        onDismissRequest = { showEditDialog = false },
                        title = { Text("Cambiar nombre") },
                        text = {
                            Column {
                                Text("Introduce tu nuevo nombre")
                                Spacer(Modifier.height(8.dp))

                                TextField(
                                    value = state.newName,
                                    onValueChange = presenter::onNewNameChange
                                )
                                state.fieldErrors["nombre"]?.let {
                                    Text(it, color = Color.Red)
                                }

                                Spacer(Modifier.height(12.dp))

                                Text("Introduce tu contraseña actual")
                                Spacer(Modifier.height(8.dp))

                                TextField(
                                    value = state.currentPasswordForName,
                                    onValueChange = presenter::onCurrentPasswordForNameChange,
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                state.fieldErrors["contrasenaActualNombre"]?.let {
                                    Text(it, color = Color.Red)
                                }

                                state.generalError?.let {
                                    Spacer(Modifier.height(8.dp))
                                    Text(it, color = Color.Red)
                                }
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    if (presenter.validateNameInputs()) {
                                        showEditDialog = false
                                        showConfirmDialog = true
                                    }
                                }
                            ) {
                                Text("Aceptar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showEditDialog = false }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
                //Confirmación del cambio de nombre
                if (showConfirmDialog) {
                    AlertDialog(
                        onDismissRequest = { showConfirmDialog = false },
                        title = { Text("Confirmar cambio") },
                        text = {
                            Text("¿Seguro que quieres cambiar el nombre a \"${state.newName}\"?")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showConfirmDialog = false
                                    presenter.submitNameChange(rememberMe = true)
                                },
                                enabled = state.newName.isNotBlank()
                            ) {
                                Text("Confirmar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showConfirmDialog = false }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    )
                }


                //Botón para cambiar la contraseña
                Button(
                    onClick = { showPasswordDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = OtroRojo),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cambiar Contraseña", color = Color.White)
                }

                if (showPasswordDialog) {
                    AlertDialog(
                        onDismissRequest = { showPasswordDialog = false },
                        title = { Text("Cambiar contraseña") },
                        text = {
                            Column {

                                Text("Introduce tu contraseña actual")
                                Spacer(Modifier.height(8.dp))

                                TextField(
                                    value = state.currentPassword,
                                    onValueChange = presenter::onCurrentPasswordChange,
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                state.fieldErrors["contrasenaActual"]?.let {
                                    Text(it, color = Color.Red)
                                }

                                Spacer(Modifier.height(16.dp))

                                Text("Introduce la nueva contraseña")
                                Spacer(Modifier.height(8.dp))

                                TextField(
                                    value = state.newPassword,
                                    onValueChange = presenter::onNewPasswordChange,
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                state.fieldErrors["contrasena"]?.let {
                                    Text(it, color = Color.Red)
                                }
                                state.fieldErrors["contrasenaIgual"]?.let {
                                    Text(it, color = Color.Red)
                                }

                                Spacer(Modifier.height(16.dp))

                                Text("Confirma la nueva contraseña")
                                Spacer(Modifier.height(8.dp))

                                TextField(
                                    value = state.confirmNewPassword,
                                    onValueChange = presenter::onConfirmNewPasswordChange,
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                state.fieldErrors["confirmarContrasena"]?.let {
                                    Text(it, color = Color.Red)
                                }

                                state.generalError?.let {
                                    Spacer(Modifier.height(8.dp))
                                    Text(it, color = Color.Red)
                                }
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    if (presenter.validatePasswordInputs()) {
                                        showPasswordDialog = false
                                        showPasswordConfirmDialog = true
                                    }
                                }
                            ) {
                                Text("Aceptar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showPasswordDialog = false }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    )
                }

                //Confirmación del cambio de contraseña
                if (showPasswordConfirmDialog) {
                    AlertDialog(
                        onDismissRequest = { showPasswordConfirmDialog = false },
                        title = { Text("Confirmar cambio") },
                        text = {
                            Text("¿Seguro que quieres cambiar la contraseña?")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showPasswordConfirmDialog = false
                                    presenter.submitPasswordChange(rememberMe = true)
                                },
                                enabled = state.currentPassword.isNotBlank() &&
                                        state.newPassword.isNotBlank() &&
                                        state.confirmNewPassword.isNotBlank()
                            ) {
                                Text("Confirmar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showPasswordConfirmDialog = false }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }

            // 🎟 PANEL DERECHO (ENTRADAS)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(panelColor, RoundedCornerShape(8.dp))
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {

                Text(
                    "Entradas",
                    color = TextWhite,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (errorMessage == null && compras.isEmpty()) {
                    Text(
                        text = "Aun no hay ventas.",
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                compras.forEach {
                    TicketRow(
                        compra = it,
                        movieTitlesById = movieTitlesById,
                        onDownloadClick = { presenter.downloadCompraPdf(it.id) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CompraQrCard(
                        compra = it,
                        movieTitlesById = movieTitlesById
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun TicketRow(
    compra: Compra,
    movieTitlesById: Map<String, String>,
    onDownloadClick: () -> Unit
) {

    val primeraEntrada = compra.lineasCompra.firstOrNull { it is LineaCompraEntrada }
    val entradaBase = primeraEntrada as? LineaCompraEntrada
    val fecha = entradaBase?.entrada?.horario?.date
    val peliculaId = entradaBase?.entrada?.peliculaId
    val tituloPelicula = peliculaId?.let { movieTitlesById[it] } ?: "Pelicula no disponible"
    val precioTotal = compra.lineasCompra.sumOf {
        when (it) {
            is LineaCompraEntrada -> it.entrada.tipo.precio.toDouble()
            is LineaCompraProducto -> it.producto.precio.toDouble()
            else -> 0.0
        }
    }.toFloat()
    val lineasEntradas = compra.lineasCompra.filterIsInstance<LineaCompraEntrada>()
    val lineasProductos = compra.lineasCompra.filterIsInstance<LineaCompraProducto>()
        .groupBy { it.producto.nombre }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            //Text(ticket.fecha.toString(), color = TextWhite)
            // 📅 FECHA
            Text(
                text = fecha?.toString() ?: "",
                color = TextWhite,
                modifier = Modifier.weight(0.3f)
            )

            // 🎬 INFO
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = tituloPelicula,
                    color = TextWhite
                )
                lineasEntradas.forEach {
                    Text(
                        text = "${it.entrada.tipo.nombre} | Fila: ${it.entrada.fila} Asiento: ${it.entrada.butaca}",
                        color = TextWhite
                    )
                    Text(
                        text = "Precio: ${it.entrada.tipo.precio}€",
                        color = TextWhite,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    )
                }

                lineasProductos.forEach { (nombre, lineas) ->
                    val cantidad = lineas.size
                    val precioUnidad = lineas.first().producto.precio
                    val precioTotalProducto = cantidad * precioUnidad
                    Text(
                        text = "Producto: $nombre | Cantidad: $cantidad | Precio/u: ${precioUnidad}€ | Total: ${precioTotalProducto}€",
                        color = TextWhite
                    )
                }
            }
            // Precio Total
            Text(
                text = "$precioTotal€",
                color = TextWhite,
                modifier = Modifier.weight(0.2f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = onDownloadClick,
                colors = ButtonDefaults.buttonColors(containerColor = OtroRojo)
            ) {
                Text("Descargar PDF", color = TextWhite)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(16.dp))

    }
}