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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Compra
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraEntrada
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraProducto
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import com.alejandro.proyecto_cines_frame.ui.logic.presenter.ProfilePresenter
import androidx.compose.runtime.collectAsState

@Composable
fun UserProfileMovile(
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

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // 👤 USER INFO
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(containerColor = OtroRojo),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al menu", color = TextWhite)
            }

            Text(
                text = "¡Hola ${state.currentName}!",
                color = TextWhite,
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Mi cuenta",
                color = TextWhite,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(8.dp))

            Text("Nombre", color = TextWhite)

            Column {
                Text(state.currentName, color = TextWhite)

                Divider(
                    color = TextWhite,
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            // 🔴 BOTONES EN FILA
            Row (
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                //Botón para cambiar el nombre

                Button(
                    onClick =  { showEditDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = OtroRojo),
                    modifier = Modifier.weight(1f)
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
                    modifier = Modifier.weight(1f)
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
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🎟 PANEL ENTRADAS
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(panelColor)
                .padding(16.dp)
        ) {

            // TÍTULO CENTRADO
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Entradas",
                    color = TextWhite,
                    style = MaterialTheme.typography.titleMedium
                )

                Divider(
                    color = TextWhite,
                    thickness = 1.dp,
                    modifier = Modifier
                        .width(100.dp)
                        .padding(top = 4.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

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
                TicketRowMobile(it, movieTitlesById)
            }
        }
    }

}
@Composable
fun TicketRowMobile(compra: Compra, movieTitlesById: Map<String, String>) {

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

        Text(
            text = fecha?.toString() ?: "",
            color = TextWhite,
            modifier = Modifier.weight(0.3f)
        )

        Spacer(Modifier.height(4.dp))

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

        Spacer(Modifier.height(4.dp))

        Text(
            text = precioTotal.toString() + "€",
            color = TextWhite,
            modifier = Modifier.weight(0.2f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider(color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(16.dp))
    }
}