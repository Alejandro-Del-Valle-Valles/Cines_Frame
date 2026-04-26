package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun SummaryStep(
    movie: String,
    seats: List<String>,
    tickets: TicketSelection,
    products: List<CartProduct>
) {
    val selectedProducts = products.filter { it.cantidad > 0 }
    val barTotal = selectedProducts.sumOf {
        (it.producto.precio * it.cantidad).toDouble()
    }.toFloat()
    val ticketsTotal = tickets.totalPrice()
    val total = ticketsTotal + barTotal

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Resumen de compra",
            style = MaterialTheme.typography.headlineMedium,
            color = TextWhite
        )

        Divider()

        Text("Película: $movie", color = TextWhite)
        Text("Butacas: ${seats.joinToString()}", color = TextWhite)

        Divider()

        Text("Entradas", color = TextWhite, style = MaterialTheme.typography.titleMedium)
        Text("Total entradas: ${tickets.total()}", color = TextWhite)
        Text("Precio entradas: ${"%.2f".format(ticketsTotal)} €", color = TextWhite)

        if (selectedProducts.isNotEmpty()) {
            Divider()

            Text("Bar", color = TextWhite, style = MaterialTheme.typography.titleMedium)

            selectedProducts.forEach {
                Text(
                    text = "${it.producto.nombre} x${it.cantidad} · ${"%.2f".format(it.lineTotal())} €",
                    color = TextWhite
                )
            }

            Text("Total bar: ${"%.2f".format(barTotal)} €", color = TextWhite)
        }

        Divider()

        Text(
            text = "TOTAL FINAL: ${"%.2f".format(total)} €",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(8.dp))
    }
}