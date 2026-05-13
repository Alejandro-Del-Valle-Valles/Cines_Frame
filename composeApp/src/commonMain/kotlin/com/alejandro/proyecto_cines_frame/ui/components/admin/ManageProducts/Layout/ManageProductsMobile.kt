package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Alergeno
import com.alejandro.proyecto_cines_frame.domain.model.Producto
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts.*
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo

@Composable
fun ManageProductsMobile(
    state: ManageProductsUiState,
    onTabChange: (ManageProductsTab) -> Unit,
    onAddProduct: () -> Unit,
    onEditProduct: (Producto) -> Unit,
    onDeleteProduct: (Producto) -> Unit,
    onAddAlergeno: () -> Unit,
    onEditAlergeno: (Alergeno) -> Unit,
    onDeleteAlergeno: (Alergeno) -> Unit
) {
    val productsFiltered =
        ManageProductsUtils.filterProducts(
            products = state.products,
            search = state.search,
            filter = state.selectedFilter
        )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = OtroRojo,
                onClick = {
                    if (
                        state.selectedTab ==
                        ManageProductsTab.PRODUCTOS
                    ) {
                        onAddProduct()
                    } else {
                        onAddAlergeno()
                    }
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(padding)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 90.dp,
                    bottom = 16.dp
                ),
            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Gestionar productos",

                style =
                    MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Administra los productos que se venden en el cine.",
                style =
                    MaterialTheme.typography.bodyMedium
            )

            ProductsTabsRow(
                selectedTab = state.selectedTab,
                onTabChange = onTabChange
            )

            if (
                state.selectedTab ==
                ManageProductsTab.PRODUCTOS
            ) {

                ProductsToolbar(
                    search = state.search,
                    selectedFilter =
                        state.selectedFilter,
                    onSearchChange = {},
                    onFilterChange = {}
                )
                LazyColumn(

                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    items(productsFiltered) { product ->

                        ProductCard(
                            product = product,
                            onEdit = onEditProduct,
                            onDelete = onDeleteProduct
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    items(state.alergenos) { alergeno ->
                        AlergenoCard(
                            alergeno = alergeno,
                            onEdit = onEditAlergeno,
                            onDelete = onDeleteAlergeno
                        )
                    }
                }
            }
        }
    }
}