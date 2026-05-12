package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductsToolbar(
    search: String,
    selectedFilter: ProductFilter,
    onSearchChange: (String) -> Unit,
    onFilterChange: (ProductFilter) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = search,
            onValueChange = onSearchChange,
            modifier = Modifier.weight(1f),
            singleLine = true,
            placeholder = { Text("Buscar producto...") }
        )

        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(
                    when (selectedFilter) {
                        ProductFilter.TODOS -> "Todos los productos"
                        ProductFilter.CON_STOCK -> "Con stock"
                        ProductFilter.SIN_STOCK -> "Sin stock"
                    }
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Todos los productos") },
                    onClick = {
                        onFilterChange(ProductFilter.TODOS)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Con stock") },
                    onClick = {
                        onFilterChange(ProductFilter.CON_STOCK)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Sin stock") },
                    onClick = {
                        onFilterChange(ProductFilter.SIN_STOCK)
                        expanded = false
                    }
                )
            }
        }
    }
}