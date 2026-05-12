package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts.Layout.ManageProductsDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts.Layout.ManageProductsMobile

@Composable
fun ManageProducts(
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

    BoxWithConstraints {

        if (
            ManageProductsUtils.esEscritorio(maxWidth)
        ) {

            ManageProductsDesktop(

                state = state,

                onBack = onBack,

                onTabChange = onTabChange,

                onAddProduct = onAddProduct,
                onEditProduct = onEditProduct,
                onDeleteProduct = onDeleteProduct,

                onAddAlergeno = onAddAlergeno,
                onEditAlergeno = onEditAlergeno,
                onDeleteAlergeno = onDeleteAlergeno
            )

        } else {

            ManageProductsMobile(

                state = state,

                onBack = onBack,

                onTabChange = onTabChange,

                onAddProduct = onAddProduct,
                onEditProduct = onEditProduct,
                onDeleteProduct = onDeleteProduct,

                onAddAlergeno = onAddAlergeno,
                onEditAlergeno = onEditAlergeno,
                onDeleteAlergeno = onDeleteAlergeno
            )
        }
    }
}