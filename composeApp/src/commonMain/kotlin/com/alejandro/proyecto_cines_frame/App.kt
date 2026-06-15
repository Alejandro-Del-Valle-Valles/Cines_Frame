package com.alejandro.proyecto_cines_frame

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alejandro.proyecto_cines_frame.domain.enums.DescuentoCondicion
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento
import com.alejandro.proyecto_cines_frame.ui.screen.MainScreen
import com.alejandro.proyecto_cines_frame.ui.screen.management.DiscountsScreen
import com.alejandro.proyecto_cines_frame.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        MainScreen()
    }
}