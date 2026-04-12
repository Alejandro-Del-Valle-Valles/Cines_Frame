package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.theme.*

@Composable
fun CheckoutContainer(
    session: Sesion,
    state: CheckoutState,
    seatMatrix: SeatMatrix,
    onBack: () -> Unit,
    onSeatClick: (SeatPosition) -> Unit,
    onContinue: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .widthIn(max = 1400.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            CheckoutHeaderTop(session, onBack)

            CheckoutStepperModern(state.step)

            when (state.step) {

                CheckoutStep.SEATS -> {
                    CheckoutSeatSection(
                        seatMatrix,
                        state.selectedSeats,
                        onSeatClick,
                        Modifier.weight(1f)
                    )
                }

                CheckoutStep.TICKETS -> {
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text("TIPO DE ENTRADA", color = TextWhite)
                    }
                }

                CheckoutStep.BAR -> {
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text("PRODUCTOS BAR", color = TextWhite)
                    }
                }

                CheckoutStep.SUMMARY -> {
                    Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text("RESUMEN", color = TextWhite)
                    }
                }
            }

            SeatLegendCentered()

            CheckoutBottomBar(state, onContinue)
        }
    }
}