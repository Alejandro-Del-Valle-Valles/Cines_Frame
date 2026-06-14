package com.alejandro.proyecto_cines_frame.ui.screen.management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.enums.DescuentoCondicion
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento
import com.alejandro.proyecto_cines_frame.ui.components.admin.DiscountsScreens.DiscountsScreenDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.DiscountsScreens.DiscountsScreenMobile
import com.alejandro.proyecto_cines_frame.ui.components.footer.FooterUtils
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader

@Composable
fun DiscountsScreen (
    discounts: List<CodigoDescuento>,
    onAddDiscount: () -> Unit,
    //onEditDiscount: (DiscountCode) -> Unit
    onEditDiscount: () -> Unit,
    onBackClick: () -> Unit
) {
    BoxWithConstraints (
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorFondoHeader)
            .padding(16.dp)
    ) {

        val esEscritorio = FooterUtils.esEscritorio(maxWidth)

        if (esEscritorio) {
            DiscountsScreenDesktop(
                discounts,
                onAddDiscount,
                onEditDiscount,
                onBackClick
            )
        } else {
            DiscountsScreenMobile(
                discounts,
                onAddDiscount,
                onEditDiscount,
                onBackClick
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun PreviewDiscountsScreen() {
    MaterialTheme() {

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