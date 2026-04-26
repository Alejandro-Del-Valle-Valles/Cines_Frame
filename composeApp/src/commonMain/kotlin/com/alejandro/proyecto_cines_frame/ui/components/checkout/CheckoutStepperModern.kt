package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.SurfaceDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun CheckoutStepperModern(
    step: CheckoutStep,
    compactLayout: Boolean,
    modifier: Modifier = Modifier
) {

    val steps = listOf(
        "Butacas",
        "Entradas",
        "Bar",
        "Resumen",
        "Pago"
    )

    val currentIndex = when (step) {
        CheckoutStep.SEATS -> 0
        CheckoutStep.TICKETS -> 1
        CheckoutStep.BAR -> 2
        CheckoutStep.SUMMARY -> 3
        CheckoutStep.PAYMENT -> 4
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth()
    ) {

        val verySmall = maxWidth < 700.dp

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(horizontal = 18.dp, vertical = 14.dp),

            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),

            maxItemsInEachRow = if (verySmall) 3 else 5
        ) {

            steps.forEachIndexed { index, label ->

                val active = index == currentIndex
                val done = index < currentIndex

                Card(
                    shape = RoundedCornerShape(999.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            active -> MaterialTheme.colorScheme.primary
                            done -> MaterialTheme.colorScheme.primary.copy(alpha = 0.75f)
                            else -> SurfaceDark
                        }
                    )
                ) {

                    Text(
                        text = label,

                        color = if (active || done)
                            TextWhite
                        else
                            TextGray,

                        style = MaterialTheme.typography.labelLarge,

                        modifier = Modifier.padding(
                            horizontal = if (verySmall) 16.dp else 22.dp,
                            vertical = 14.dp
                        )
                    )
                }
            }
        }
    }
}