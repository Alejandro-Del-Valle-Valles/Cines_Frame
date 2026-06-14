package com.alejandro.proyecto_cines_frame.ui.components.admin.DiscountsScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

import androidx.compose.foundation.lazy.items

@Composable
fun DiscountsScreenMobile  (
    discounts: List<CodigoDescuento>,
    onAddDiscount: () -> Unit,
    //onEditDiscount: (DiscountCode) -> Unit
    onEditDiscount: () -> Unit,
    onBackClick: () -> Unit
) {
    Column (
    modifier = Modifier
    .fillMaxSize()
    .background(BackgroundDark)
    .padding(6.dp)
    ) {

        HeaderMobile(
            onAddDiscount = onAddDiscount
        )

        Spacer(Modifier.height(12.dp))

        DiscountsListMobile(
            discounts = discounts,
            onEditDiscount = onEditDiscount
        )
    }
}

@Composable
private fun HeaderMobile(
    onAddDiscount: () -> Unit
) {

    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Gestión de códigos Promocionales",
            color = TextWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = onAddDiscount,
            modifier = Modifier.height(30.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = OtroRojo
            ),
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 0.dp
            )
        ) {

            Text(
                text = "Añadir código",
                fontSize = 11.sp,
                color = TextWhite
            )
        }
    }
}

@Composable
private fun DiscountsListMobile(
    discounts: List<CodigoDescuento>,
    onEditDiscount: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorFondoHeader)
            .padding(
                horizontal = 8.dp,
                vertical = 12.dp
            )
    ) {
        LazyColumn {
            items(discounts) { discount ->

                DiscountRowMobile(
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
private fun DiscountRowMobile(
    discount: CodigoDescuento,
    onEditDiscount: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = discount.codigo,
            color = TextWhite,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(2.5f)
        )

        Text(
            text = "${discount.porcentaje}%",
            color = TextWhite,
            fontSize = 10.sp,
            modifier = Modifier.weight(1.4f)
        )

        Text(
            text = if (discount.activo) "Activo" else "Inactivo",
            color = TextWhite,
            fontSize = 10.sp,
            modifier = Modifier.weight(1.3f)
        )

        Button(
            onClick = {
                onEditDiscount()
            },
            modifier = Modifier
                .height(22.dp)
                .weight(1.8f),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = OtroRojo
            ),
            contentPadding = PaddingValues(0.dp)
        ) {

            Text(
                text = "Modificar",
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 9.sp
            )
        }

    }
}