package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductCard(
    product: ProductUiModel,
    onEdit: (ProductUiModel) -> Unit,
    onDelete: (ProductUiModel) -> Unit
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
            if (product.descripcion.isNotBlank()) {
                Text(product.descripcion, style = MaterialTheme.typography.bodySmall)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("€${"%.2f".format(product.precio)}")
                Text("Stock: ${product.stock}")
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                if (product.alergenos.isEmpty()) {
                    Text("—")
                } else {
                    product.alergenos.take(3).forEach {
                        AlergenoChip(nombre = it)
                    }
                    if (product.alergenos.size > 3) {
                        AlergenoChip(nombre = "+${product.alergenos.size - 3}")
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