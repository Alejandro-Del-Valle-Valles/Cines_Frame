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
import com.alejandro.proyecto_cines_frame.domain.model.Entrada
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun UserProfileMovile(
    userName: String,
    tickets: List<EntradasListaModel>,
    onChangeName: () -> Unit,
    onChangePassword: () -> Unit,
    onNameChanged: (String) -> Unit
) {
    val background = BackgroundDark
    val panelColor = ColorFondoHeader
    var showEditDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf(userName) }
    var showPasswordDialog by remember { mutableStateOf(false) }
    var showPasswordConfirmDialog by remember { mutableStateOf(false) }

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

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

            Text(
                text = "¡Hola $userName!",
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
                Text(userName, color = TextWhite)

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
                                    value = newName,
                                    onValueChange = { newName = it }
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showEditDialog = false
                                    showConfirmDialog = true
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
                            Text("¿Seguro que quieres cambiar el nombre a \"$newName\"?")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showConfirmDialog = false
                                    onNameChanged(newName)

                                    // TODO: llamar a la API para actualizar nombre

                                },
                                enabled = newName.isNotBlank()
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
                                    value = currentPassword,
                                    onValueChange = { currentPassword = it },
                                    visualTransformation = PasswordVisualTransformation()
                                )

                                Spacer(Modifier.height(16.dp))

                                Text("Introduce la nueva contraseña")
                                Spacer(Modifier.height(8.dp))

                                TextField(
                                    value = newPassword,
                                    onValueChange = { newPassword = it },
                                    visualTransformation = PasswordVisualTransformation()
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showPasswordDialog = false
                                    showPasswordConfirmDialog = true
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

                                    // 🔥 AQUÍ irá tu lógica real
                                    // TODO: validar contraseña actual con backend
                                    // TODO: enviar nueva contraseña
                                    // TODO: llamar a la API para actualizar nombre


                                    // limpiar campos
                                    currentPassword = ""
                                    newPassword = ""
                                },
                                enabled = currentPassword.isNotBlank() && newPassword.isNotBlank()
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

            tickets.forEach {
                TicketRow(it)
            }
        }
    }

}
@Composable
fun TicketRowMobile(ticket: EntradasListaModel) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = ticket.fecha.toString(),
            color = TextWhite,
            modifier = Modifier.weight(0.3f)
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = ticket.tituloPelicula,
            color = TextWhite
        )
        Text(
            text = ticket.cantidadEntradas.toString() + "x " + ticket.tipoEntrada,
            color = TextWhite
        )
        //TODO: Se debería hacer un bucle para marcar el tipo de entradas diferentes o con un if-elif-else

        Spacer(Modifier.height(4.dp))

        Text(
            text = ticket.precioTotal.toString() + "€",
            color = TextWhite,
            modifier = Modifier.weight(0.2f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider(color = Color.LightGray.copy(alpha = 0.4f))

        Spacer(modifier = Modifier.height(16.dp))
    }
}