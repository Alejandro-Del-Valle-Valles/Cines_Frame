package com.alejandro.proyecto_cines_frame.ui.components.admin.RoomManagementScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.proyecto_cines_frame.domain.model.Sala
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun RoomManagementDesktop(
    rooms: List<Sala>,
    onAddRoom: () -> Unit,
    onEditRoom: (Sala) -> Unit,
    onDeleteRoom: (Sala) -> Unit
) {

    // 🔥 Popup borrado
    var roomToDelete by remember {
        mutableStateOf<Sala?>(null)
    }

    val background = BackgroundDark
    val panelColor = ColorFondoHeader

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(30.dp)
    ) {
        // HEADER FIJO
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Gestión de salas",
                color = TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )

            Button(
                onClick = onAddRoom,
                colors = ButtonDefaults.buttonColors(containerColor = OtroRojo),
                modifier = Modifier
                    .width(320.dp)
                    .height(48.dp)
            ) {

                Text(
                    text = "Añadir sala",
                    color = TextWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // CONTENEDOR LISTA

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(panelColor)
                .padding(20.dp)
        ) {

            // SÓLO ESTA PARTE SCROLLEA

            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(rooms) { room ->

                    Column {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // 🏢 NOMBRE
                            Text(
                                text = "Sala " + room.numero,
                                color = TextWhite,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge
                            )

                            // 👥 AFORO
                            Text(
                                text = "Aforo: ${room.aforo} butacas",
                                color = TextWhite,
                                modifier = Modifier.weight(1f)
                            )

                            // ✏ MODIFICAR
                            Button(
                                onClick = {
                                    onEditRoom(room)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = OtroRojo),
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(38.dp)
                            ) {

                                Text(
                                    text = "Modificar",
                                    color = TextWhite
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // 🗑 ELIMINAR
                            IconButton(
                                onClick = {
                                    roomToDelete = room
                                }
                            ) {

                                Text(
                                    text = "🗑",
                                    color = OtroRojo,
                                    fontSize = 22.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        HorizontalDivider(
                            color = TextWhite.copy(alpha = 0.7f),
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }

    // 🔥 POPUP CONFIRMACIÓN BORRADO

    if (roomToDelete != null) {

        AlertDialog(
            onDismissRequest = {
                roomToDelete = null
            },

            title = {
                Text("Confirmar borrado")
            },

            text = {
                Text(
                    "¿Seguro que quieres eliminar la sala número ${roomToDelete!!.numero}?"
                )
            },

            confirmButton = {

                Button(
                    onClick = {
                        onDeleteRoom(roomToDelete!!)
                        roomToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OtroRojo
                    )
                ) {
                    Text("Eliminar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        roomToDelete = null
                    }
                ) {

                    Text("Cancelar")
                }
            }
        )
    }
}