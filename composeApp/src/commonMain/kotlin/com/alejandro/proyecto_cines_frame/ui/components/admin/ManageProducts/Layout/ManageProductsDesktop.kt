package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts.*
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun ManageProductsDesktop(

    state: ManageProductsUiState,

    onBack: () -> Unit,

    onTabChange: (ManageProductsTab) -> Unit,

    onAddProduct: () -> Unit,
    onEditProduct: (ProductUiModel) -> Unit,
    onDeleteProduct: (ProductUiModel) -> Unit,

    onAddAlergeno: () -> Unit,
    onEditAlergeno: (AlergenoUiModel) -> Unit,
    onDeleteAlergeno: (AlergenoUiModel) -> Unit

) {

    val productsFiltered =
        ManageProductsUtils.filterProducts(
            products = state.products,
            search = state.search,
            filter = state.selectedFilter
        )

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 72.dp,
                bottom = 24.dp
            ),

        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    text = "Gestionar productos",

                    color = TextWhite,

                    style =
                        MaterialTheme.typography.headlineMedium
                )

                Text(
                    text =
                        "Administra los productos que se venden en el cine.",

                    color = TextWhite
                )
            }

            Button(
                onClick = onAddProduct
            ) {

                Text("+  Nuevo producto")
            }
        }

        ProductsTabsRow(
            selectedTab = state.selectedTab,
            onTabChange = onTabChange
        )

        Row(

            modifier = Modifier.fillMaxSize(),

            horizontalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            Card(

                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),

                colors = CardDefaults.cardColors(
                    containerColor = ColorFondoHeader
                )
            ) {

                Column(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(16.dp)
                ) {

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

                        ProductsTable(
                            products = productsFiltered,
                            onEdit = onEditProduct,
                            onDelete = onDeleteProduct
                        )

                    } else {

                        AlergenosList(
                            alergenos = state.alergenos,

                            onAddAlergeno = onAddAlergeno,

                            onEditAlergeno = onEditAlergeno,

                            onDeleteAlergeno = onDeleteAlergeno
                        )
                    }
                }
            }

            Card(

                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),

                colors = CardDefaults.cardColors(
                    containerColor = ColorFondoHeader
                )
            ) {

                AlergenosSidePanel(

                    alergenos = state.alergenos,

                    onAddAlergeno = onAddAlergeno,

                    onEditAlergeno = onEditAlergeno,

                    onDeleteAlergeno = onDeleteAlergeno
                )
            }
        }
    }
}