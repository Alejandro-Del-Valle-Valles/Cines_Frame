package com.alejandro.proyecto_cines_frame.ui.components.admin.RoomManagementScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.alejandro.proyecto_cines_frame.domain.model.Sala
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun RoomManagementMovile(
    rooms: List<Sala>,
    onAddRoom: () -> Unit,
    onEditRoom: (Sala) -> Unit,
    onDeleteRoom: (Sala) -> Unit
) {


    val background = BackgroundDark
    val panelColor = ColorFondoHeader

    // 🔥 Popup confirmación borrado
    var roomToDelete by remember {
        mutableStateOf<Sala?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(14.dp)
    ) {

        // HEADER FIJO (NO SCROLLEA)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

            Text(
                text = "Gestión de salas",
                color = TextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )

            Button(
                onClick = onAddRoom,
                colors = ButtonDefaults.buttonColors(
                    containerColor = OtroRojo
                ),
                shape = RectangleShape,
                contentPadding = PaddingValues(
                    horizontal = 18.dp,
                    vertical = 6.dp
                )
            ) {

                Text(
                    text = "Añadir sala",
                    color = TextWhite,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // SÓLO LA LISTA HACE SCROLL

        LazyColumn(
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
                            text = "Sala ${room.numero}",
                            color = TextWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(1f)
                        )

                        // 👥 AFORO
                        Text(
                            text = "Aforo: ${room.aforo} butacas",
                            color = TextWhite,
                            fontSize = 11.sp,
                            modifier = Modifier.weight(1.4f)
                        )

                        // ✏ MODIFICAR
                        Button(
                            onClick = {
                                onEditRoom(room)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OtroRojo
                            ),
                            shape = RectangleShape,
                            modifier = Modifier.height(28.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {

                            Text(
                                text = "Modificar",
                                color = TextWhite,
                                fontSize = 10.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        // 🗑 ELIMINAR
                        IconButton(
                            onClick = {
                                roomToDelete = room
                            },
                            modifier = Modifier.size(22.dp)
                        ) {

                            Text(
                                text = "🗑",
                                color = OtroRojo,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(
                        color = TextWhite.copy(alpha = 0.7f),
                        thickness = 1.dp
                    )
                }
            }
        }
    }

    // POPUP CONFIRMACIÓN BORRADO

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
                    "¿Seguro que quieres eliminar la sala ${roomToDelete!!.numero}?"
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        // 🔥 TEST
                        println("Eliminar sala ${roomToDelete!!.numero}")

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

