package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun BarStep(
    products: List<CartProduct>,
    onUpdate: (List<CartProduct>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Productos del bar",
            style = MaterialTheme.typography.headlineMedium,
            color = TextWhite
        )

        if (products.isEmpty()) {
            Text(
                text = "No hay productos disponibles.",
                color = TextGray,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        products.forEachIndexed { index, item ->
            val canAddMore = item.cantidad < item.producto.stock

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = item.producto.nombre,
                        color = TextWhite,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "${"%.2f".format(item.producto.precio)} €",
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Stock disponible: ${item.producto.stock}",
                        color = TextGray,
                        style = MaterialTheme.typography.bodySmall
                    )

                    if (item.producto.alergenos.isNotEmpty()) {
                        Text(
                            text = "Alérgenos: ${item.producto.alergenos.joinToString { it.nombre }}",
                            color = TextGray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedButton(
                            onClick = {
                                if (item.cantidad > 0) {
                                    val updated = products.toMutableList()
                                    updated[index] = item.copy(cantidad = item.cantidad - 1)
                                    onUpdate(updated)
                                }
                            },
                            enabled = item.cantidad > 0
                        ) {
                            Text("-")
                        }

                        Text(
                            text = item.cantidad.toString(),
                            color = TextWhite,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        Button(
                            onClick = {
                                if (canAddMore) {
                                    val updated = products.toMutableList()
                                    updated[index] = item.copy(cantidad = item.cantidad + 1)
                                    onUpdate(updated)
                                }
                            },
                            enabled = canAddMore
                        ) {
                            Text("+")
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}