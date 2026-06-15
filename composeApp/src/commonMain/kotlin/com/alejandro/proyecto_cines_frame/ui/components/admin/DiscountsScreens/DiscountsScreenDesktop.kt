package com.alejandro.proyecto_cines_frame.ui.components.admin.DiscountsScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import androidx.compose.foundation.lazy.items



@Composable
fun DiscountsScreenDesktop (
    discounts: List<CodigoDescuento>,
    onAddDiscount: () -> Unit,
    onEditDiscount: (CodigoDescuento) -> Unit,
    onBackClick: () -> Unit
) {
    Column (
    modifier = Modifier
    .fillMaxSize()
    .background(BackgroundDark)
    .padding(
    horizontal = 30.dp,
    vertical = 15.dp
    )
    ) {

        Header(
            onAddDiscount = onAddDiscount
        )

        Spacer(Modifier.height(30.dp))

        DiscountsList(
            discounts = discounts,
            onEditDiscount = onEditDiscount
        )
    }
}

@Composable
private fun Header(
    onAddDiscount: () -> Unit
) {

    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Gestión de códigos promocionales",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onAddDiscount,
            modifier = Modifier
                .width(330.dp)
                .height(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = OtroRojo
            ),
            shape = RectangleShape
        ) {
            Text(
                "Añadir código",
                color = TextWhite,
                letterSpacing = 2.sp
            )
        }
    }
}

@Composable
private fun DiscountsList(
    discounts: List<CodigoDescuento>,
    onEditDiscount: (CodigoDescuento) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .background(ColorFondoHeader)
            .padding(
                horizontal = 20.dp,
                vertical = 25.dp
            )
    ) {

        LazyColumn {
            items(discounts) { discount ->

                DiscountRow(
                    discount = discount,
                    onEditDiscount = onEditDiscount
                )

                HorizontalDivider(
                    color = TextWhite,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
private fun DiscountRow(
    discount: CodigoDescuento,
    onEditDiscount: (CodigoDescuento) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = discount.codigo,
            color = TextWhite,
            modifier = Modifier.weight(3f)
        )

        Text(
            text = "Porcentaje: ${discount.porcentajeDescuento}%",
            color = TextWhite,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = if (discount.activo) "Activo" else "Inactivo",
            color = TextWhite,
            modifier = Modifier.weight(1.5f)
        )

        Spacer(Modifier.weight(1f))

        Box( // Botón de editar
            modifier = Modifier.weight(1.5f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Button(
                onClick = {
                    onEditDiscount(discount)
                },
                modifier = Modifier
                    .width(145.dp)
                    .height(35.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = OtroRojo
                )
            ) {

                Text(
                    "Modificar",
                    fontSize = 13.sp,
                    color = TextWhite
                )
            }
        }
    }
}