package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray

@Composable
fun CheckoutSeatSection(
    seatMatrix: SeatMatrix,
    selectedSeats: Set<SeatPosition>,
    onSeatClick: (SeatPosition) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("PANTALLA", color = TextGray)

        Spacer(Modifier.height(16.dp))

        SeatMap(
            seatMatrix = seatMatrix,
            selectedSeats = selectedSeats,
            onSeatClick = onSeatClick
        )
    }
}