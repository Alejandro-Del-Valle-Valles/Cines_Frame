package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Producto

@Composable
fun ProductsTable(
    products: List<Producto>,
    onEdit: (Producto) -> Unit,
    onDelete: (Producto) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                "Producto",
                modifier = Modifier.weight(2f)
            )

            Text(
                "Precio",
                modifier = Modifier.weight(0.9f)
            )

            Text(
                "Stock",
                modifier = Modifier.weight(0.7f)
            )

            Text(
                "Alérgenos",
                modifier = Modifier.weight(1.8f)
            )

            Text(
                "Acciones",
                modifier = Modifier.weight(0.8f)
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        HorizontalDivider()

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        LazyColumn(

            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            items(products) { product ->

                ProductRow(
                    product = product,
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        }
    }
}