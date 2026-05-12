package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlergenoCard(
    alergeno: AlergenoUiModel,
    onEdit: (AlergenoUiModel) -> Unit,
    onDelete: (AlergenoUiModel) -> Unit
) {
    Card {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(alergeno.nombre)
            Row {
                TextButton(onClick = { onEdit(alergeno) }) { Text("✏️") }
                TextButton(onClick = { onDelete(alergeno) }) { Text("🗑️") }
            }
        }
    }
}