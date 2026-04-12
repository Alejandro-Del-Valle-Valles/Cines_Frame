package com.alejandro.proyecto_cines_frame.ui.components.checkout

enum class CheckoutStep {
    SEATS, TICKETS, BAR, SUMMARY
}

data class SeatPosition(
    val row: Int,
    val column: Int
)

typealias SeatMatrix = List<List<Boolean?>>