package com.alejandro.proyecto_cines_frame

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts.ManageProductsScreen
import com.alejandro.proyecto_cines_frame.ui.theme.AppTheme


@Composable
@Preview
fun App() {
    var currentScreen by remember {
        mutableStateOf("admin")
    }

    AppTheme {
        ManageProductsScreen(
            onBack = {}
        )
    }
}
