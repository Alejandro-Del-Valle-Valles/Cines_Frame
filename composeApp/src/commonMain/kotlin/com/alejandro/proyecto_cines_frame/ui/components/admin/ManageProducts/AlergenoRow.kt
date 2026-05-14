package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alejandro.proyecto_cines_frame.domain.model.Alergeno

@Composable
fun AlergenoRow(
    alergeno: Alergeno,
    onEdit: (Alergeno) -> Unit,
    onDelete: (Alergeno) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(alergeno.nombre)
        Row {
            TextButton(onClick = { onEdit(alergeno) }) { Text("✏️") }
            TextButton(onClick = { onDelete(alergeno) }) { Text("🗑️") }
        }
    }
}