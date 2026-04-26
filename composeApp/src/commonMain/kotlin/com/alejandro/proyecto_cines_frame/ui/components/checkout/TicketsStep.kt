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
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun TicketsStep(
    seatsSelected: Int,
    tickets: TicketSelection,
    onChange: (TicketSelection) -> Unit
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

        TicketRow(
            title = "Adulto",
            description = "Desde 13 años · 8,50 €",
            value = tickets.adulto,
            canAdd = canAddMore,
            onMinus = {
                if (tickets.adulto > 0) onChange(tickets.copy(adulto = tickets.adulto - 1))
            },
            onPlus = {
                if (canAddMore) onChange(tickets.copy(adulto = tickets.adulto + 1))
            }
        )

        TicketRow(
            title = "Niño",
            description = "Hasta 12 años (Incluidos) · 6,00 €",
            value = tickets.nino,
            canAdd = canAddMore,
            onMinus = {
                if (tickets.nino > 0) onChange(tickets.copy(nino = tickets.nino - 1))
            },
            onPlus = {
                if (canAddMore) onChange(tickets.copy(nino = tickets.nino + 1))
            }
        )

        TicketRow(
            title = "Senior",
            description = "Mayores de 65 años · 7,00 €",
            value = tickets.senior,
            canAdd = canAddMore,
            onMinus = {
                if (tickets.senior > 0) onChange(tickets.copy(senior = tickets.senior - 1))
            },
            onPlus = {
                if (canAddMore) onChange(tickets.copy(senior = tickets.senior + 1))
            }
        )

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