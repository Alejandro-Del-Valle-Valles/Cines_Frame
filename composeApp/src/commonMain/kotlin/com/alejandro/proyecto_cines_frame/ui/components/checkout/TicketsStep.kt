// TicketsStep.kt
package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
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
import com.alejandro.proyecto_cines_frame.domain.model.TipoEntrada
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun TicketsStep(
    seatsSelected: Int,
    tiposEntrada: List<TipoEntrada>,
    tickets: TipoEntradaSelection,
    onChange: (TipoEntradaSelection) -> Unit
) {
    val total = tickets.total()
    val canAddMore = total < seatsSelected

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Tipo de entrada",
            style = MaterialTheme.typography.headlineMedium,
            color = TextWhite
        )

        if (tiposEntrada.isEmpty()) {
            Text(
                text = "No hay tipos de entrada disponibles.",
                color = TextGray,
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            tiposEntrada.forEach { tipo ->
                val value = tickets.cantidadFor(tipo.id)
                val priceLabel = "${"%.2f".format(tipo.precio)} €"
                val description = if (tipo.descripcion.isNotBlank()) {
                    "${tipo.descripcion} · $priceLabel"
                } else {
                    priceLabel
                }

                TicketRow(
                    title = tipo.nombre,
                    description = description,
                    value = value,
                    canAdd = canAddMore,
                    onMinus = {
                        if (value > 0) onChange(tickets.update(tipo.id, value - 1))
                    },
                    onPlus = {
                        if (canAddMore) onChange(tickets.update(tipo.id, value + 1))
                    }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Entradas seleccionadas: $total / $seatsSelected",
            color = if (total == seatsSelected) MaterialTheme.colorScheme.primary else TextGray,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun TicketRow(
    title: String,
    description: String,
    value: Int,
    canAdd: Boolean,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(title, color = TextWhite, style = MaterialTheme.typography.titleMedium)
            Text(description, color = TextGray, style = MaterialTheme.typography.bodySmall)

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onMinus,
                    enabled = value > 0,
                    modifier = Modifier.sizeIn(minWidth = 44.dp)
                ) {
                    Text("-")
                }

                Text(
                    text = value.toString(),
                    color = TextWhite,
                    modifier = Modifier.padding(top = 12.dp)
                )

                Button(
                    onClick = onPlus,
                    enabled = canAdd,
                    modifier = Modifier.sizeIn(minWidth = 44.dp)
                ) {
                    Text("+")
                }
            }
        }
    }
}