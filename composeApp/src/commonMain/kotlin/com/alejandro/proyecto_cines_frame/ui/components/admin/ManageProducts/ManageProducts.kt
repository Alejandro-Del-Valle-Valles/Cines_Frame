package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import com.alejandro.proyecto_cines_frame.domain.model.Alergeno
import com.alejandro.proyecto_cines_frame.domain.model.Producto
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts.Layout.ManageProductsDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts.Layout.ManageProductsMobile

@Composable
fun ManageProducts(
    state: ManageProductsUiState,
    onTabChange: (ManageProductsTab) -> Unit,
    onAddProduct: () -> Unit,
    onEditProduct: (Producto) -> Unit,
    onDeleteProduct: (Producto) -> Unit,
    onAddAlergeno: () -> Unit,
    onEditAlergeno: (Alergeno) -> Unit,
    onDeleteAlergeno: (Alergeno) -> Unit
) {

    BoxWithConstraints {

        if (ManageProductsUtils.esEscritorio(maxWidth)) {
            ManageProductsDesktop(
                state = state,
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