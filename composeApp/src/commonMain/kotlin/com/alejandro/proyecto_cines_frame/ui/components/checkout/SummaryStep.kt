package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento
import com.alejandro.proyecto_cines_frame.domain.model.TipoEntrada
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun SummaryStep(
    movie: String,
    seats: List<String>,
    tiposEntrada: List<TipoEntrada>,
    tickets: TipoEntradaSelection,
    products: List<CartProduct>,
    discountCode: String,
    appliedDiscount: CodigoDescuento?,
    discountError: String?,
    isApplyingDiscount: Boolean,
    onDiscountCodeChange: (String) -> Unit,
    onApplyDiscount: () -> Unit
) {
    val selectedProducts = products.filter { it.cantidad > 0 }
    val barTotal = selectedProducts.sumOf {
        (it.producto.precio * it.cantidad).toDouble()
    }.toFloat()
    val ticketsTotal = tickets.totalPrice(tiposEntrada)
    val pricing = calculateCheckoutPricing(ticketsTotal, barTotal, appliedDiscount)
    val discountAmount = pricing.discountAmount

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

        tiposEntrada.forEach { tipo ->
            val cantidad = tickets.cantidadFor(tipo.id)
            if (cantidad > 0) {
                val lineTotal = tipo.precio * cantidad
                Text(
                    text = "${tipo.nombre} x$cantidad · ${"%.2f".format(lineTotal)} €",
                    color = TextWhite
                )
            }
        }

        Text("Precio entradas: ${"%.2f".format(ticketsTotal)} €", color = TextWhite)

        Divider()

        Text("Código de descuento", color = TextWhite, style = MaterialTheme.typography.titleMedium)

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(
                value = discountCode,
                onValueChange = onDiscountCodeChange,
                label = { Text("Introduce tu código") },
                supportingText = {
                    if (!discountError.isNullOrBlank()) {
                        Text(discountError)
                    } else {
                        Text("Solo se puede aplicar un código a la vez.")
                    }
                },
                isError = !discountError.isNullOrBlank(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onApplyDiscount,
                enabled = !isApplyingDiscount,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isApplyingDiscount) "Aplicando..." else "Aplicar código")
            }
        }

        appliedDiscount?.let { discount ->
            Text(
                text = "Código aplicado: ${discount.codigo} (${discount.porcentajeDescuento}% sobre ${discount.targetLabel()})",
                color = TextWhite
            )
            Text(
                text = "Descuento aplicado: -${"%.2f".format(discountAmount)} €",
                color = TextWhite
            )
        }


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

        Text("Subtotal: ${"%.2f".format(pricing.subtotal)} €", color = TextWhite)

        if (discountAmount > 0f) {
            Text("Descuento: -${"%.2f".format(discountAmount)} €", color = TextWhite)
        }

        Text(
            text = "TOTAL FINAL: ${"%.2f".format(pricing.finalTotal)} €",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(8.dp))
    }
}