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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Compra
import com.alejandro.proyecto_cines_frame.domain.model.Entrada
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun UserProfileDesktop(
    userName: String,
    tickets: List<Entrada>,
    onChangeName: () -> Unit,
    onChangePassword: () -> Unit
) {

    val background = BackgroundDark
    val panelColor = ColorFondoHeader

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {

        //
        //TODO: Poner el header aquí
        //


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

                Text("¡Hola $userName!", color = TextWhite, style = MaterialTheme.typography.titleLarge)

                Text("Mi cuenta", color = TextWhite)

                Column {
                    Text("Nombre", color = TextWhite)
                    Text(userName, color = TextWhite)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onChangeName,
                    colors = ButtonDefaults.buttonColors(containerColor = OtroRojo)
                ) {
                    Text("Cambiar Nombre", color = TextWhite)
                }

                Button(
                    onClick = onChangePassword,
                    colors = ButtonDefaults.buttonColors(containerColor = OtroRojo)
                ) {
                    Text("Cambiar Contraseña", color = TextWhite)
                }
            }

            // 🎟 PANEL DERECHO (ENTRADAS)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(panelColor, RoundedCornerShape(8.dp))
                    .padding(24.dp)
            ) {

                Text(
                    "Entradas",
                    color = TextWhite,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                tickets.forEach {
                    //
                    //TicketRow(it)
                    //
                }
            }
        }
    }
}

@Composable
fun TicketRow(ticket: Entrada, tituloPelicula: String, cantidadEntradas: Int, tipoEntrada: String, precioTotal: Float) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(ticket.horario.toString(), color = TextWhite)

            Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Text(tituloPelicula, color = TextWhite)
                Text(cantidadEntradas.toString() + "x " + tipoEntrada, color = TextGray)
            }

            Text(precioTotal.toString(), color = TextWhite)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Divider(color = TextGray)

    }
}