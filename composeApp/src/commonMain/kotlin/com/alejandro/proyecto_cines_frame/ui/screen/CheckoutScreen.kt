package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.components.checkout.*
import com.alejandro.proyecto_cines_frame.ui.components.footer.Footer
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import androidx.compose.foundation.background

@Composable
fun CheckoutScreen(
    session: Sesion,
    seatMatrix: SeatMatrix,
    onBack: () -> Unit
) {
    var state by remember { mutableStateOf(CheckoutState()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CheckoutContainer(
                session = session,
                state = state,
                seatMatrix = seatMatrix,
                onBack = onBack,
                onSeatClick = { clicked ->
                    val current = state.selectedSeats.toMutableSet()
                    if (!current.add(clicked)) current.remove(clicked)
                    state = state.copy(selectedSeats = current)
                },
                onContinue = {
                    state = state.copy(step = nextStep(state.step))
                }
            )
        }

        Footer()
    }
}