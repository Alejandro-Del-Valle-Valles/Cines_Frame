package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun ProductsTabsRow(
    selectedTab: ManageProductsTab,
    onTabChange: (ManageProductsTab) -> Unit
) {
    TabRow(selectedTabIndex = selectedTab.ordinal) {
        Tab(
            selected = selectedTab == ManageProductsTab.PRODUCTOS,
            onClick = { onTabChange(ManageProductsTab.PRODUCTOS) },
            text = { Text("Productos") }
        )
        Tab(
            selected = selectedTab == ManageProductsTab.ALERGENOS,
            onClick = { onTabChange(ManageProductsTab.ALERGENOS) },
            text = { Text("Alergenos") }
        )
    }
}