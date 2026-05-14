package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Producto

@Composable
fun ProductRow(
    product: Producto,
    onEdit: (Producto) -> Unit,
    onDelete: (Producto) -> Unit
) {

    Row(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        horizontalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        Column(
            modifier = Modifier.weight(2f)
        ) {

            Text(
                text = product.nombre
            )
        }

        Text(
            text =
                "€${"%.2f".format(product.precio)}",

            modifier = Modifier.weight(0.9f)
        )

        Text(
            text = product.stock.toString(),

            modifier = Modifier.weight(0.7f)
        )

        FlowRow(

            modifier = Modifier.weight(1.8f),

            horizontalArrangement =
                Arrangement.spacedBy(6.dp),

            verticalArrangement =
                Arrangement.spacedBy(6.dp)

        ) {

            if (
                product.alergenos.isEmpty()
            ) {

                Text("—")

            } else {

                product.alergenos.forEach {

                    AlergenoChip(
                        nombre = it.nombre
                    )
                }
            }
        }

        Row(
            modifier = Modifier.weight(0.8f)
        ) {

            TextButton(
                onClick = {
                    onEdit(product)
                }
            ) {
                Text("✏️")
            }

            TextButton(
                onClick = {
                    onDelete(product)
                }
            ) {
                Text("🗑️")
            }
        }
    }
}