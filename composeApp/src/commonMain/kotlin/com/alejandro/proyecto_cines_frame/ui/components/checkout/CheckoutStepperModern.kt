package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.*

@Composable
fun CheckoutStepperModern(current: CheckoutStep) {

    val steps = listOf(
        "Elegir asiento",
        "Tipo de entrada",
        "Productos de bar",
        "Resumen"
    )

    Column(modifier = Modifier.padding(horizontal = 24.dp)) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            steps.forEachIndexed { index, label ->

                val selected = index == current.ordinal

                Row {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                if (selected) PrimaryRed else TextGray,
                                CircleShape
                            )
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        label,
                        color = if (selected) TextWhite else TextGray
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(TextGray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth((current.ordinal + 1) / 4f)
                    .fillMaxHeight()
                    .background(PrimaryRed)
            )
        }
    }
}