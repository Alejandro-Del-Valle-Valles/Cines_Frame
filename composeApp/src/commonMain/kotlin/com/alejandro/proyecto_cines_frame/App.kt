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
        //MainScreen()
        val discounts = listOf(
            CodigoDescuento(1,"DESCUENTO10", DescuentoCondicion.TODOS, 10, true),
            CodigoDescuento(2,"PELICULA05", DescuentoCondicion.TODOS, 5, true),
            CodigoDescuento(3,"ENEROLOCO", DescuentoCondicion.TODOS, 15, false),
            CodigoDescuento(4,"COMIDA5", DescuentoCondicion.TODOS, 5, true),
            CodigoDescuento(5,"DESCUENTO20", DescuentoCondicion.TODOS, 20, false)
        )

        DiscountsScreen(
            discounts = discounts,
            onAddDiscount = {},
            onEditDiscount = {},
            onBackClick = {}
        )
    }
}