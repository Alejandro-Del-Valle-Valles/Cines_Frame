package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlergenosSidePanel(
    alergenos: List<AlergenoUiModel>,
    onAddAlergeno: () -> Unit,
    onEditAlergeno: (AlergenoUiModel) -> Unit,
    onDeleteAlergeno: (AlergenoUiModel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Alergenos", style = MaterialTheme.typography.titleMedium)
            Button(onClick = onAddAlergeno) { Text("+  Nuevo alérgeno") }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(alergenos) { alergeno ->
                AlergenoRow(
                    alergeno = alergeno,
                    onEdit = onEditAlergeno,
                    onDelete = onDeleteAlergeno
                )
            }
        }

        TextButton(onClick = {}) {
            Text("Ver todos los alérgenos")
        }
    }
}