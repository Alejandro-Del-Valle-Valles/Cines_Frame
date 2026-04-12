package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.alejandro.proyecto_cines_frame.ui.theme.*

@Composable
fun CheckoutBottomBar(
    state: CheckoutState,
    onContinue: () -> Unit
) {
    val canContinue = state.selectedSeats.isNotEmpty()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = if (!canContinue)
                "Debes elegir al menos un asiento para continuar"
            else "",
            color = TextGray
        )

        Button(
            onClick = onContinue,
            enabled = canContinue,
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (canContinue) PrimaryRed else TextGray
            )
        ) {
            Text("CONTINUAR")
        }
    }
}