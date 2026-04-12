package com.alejandro.proyecto_cines_frame.ui.components.checkout

data class CheckoutState(
    val step: CheckoutStep = CheckoutStep.SEATS,
    val selectedSeats: Set<SeatPosition> = emptySet()
)