package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Producto

@Composable
fun ProductCard(
    product: Producto,
    onEdit: (Producto) -> Unit,
    onDelete: (Producto) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(product.nombre, style = MaterialTheme.typography.titleMedium)

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("€${"%.2f".format(product.precio)}")
                Text("Stock: ${product.stock}")
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                val alergenos = product.alergenos.toList()
                if (alergenos.isEmpty()) {
                    Text("—")
                } else {
                    alergenos.take(3).forEach {
                        AlergenoChip(nombre = it.nombre)
                    }
                    if (alergenos.size > 3) {
                        AlergenoChip(nombre = "+${alergenos.size - 3}")
                    }
                }
            }

            Row {
                TextButton(onClick = { onEdit(product) }) { Text("✏️") }
                TextButton(onClick = { onDelete(product) }) { Text("🗑️") }
            }
        }
    }
}