package com.alejandro.proyecto_cines_frame.ui.components.checkout

import com.alejandro.proyecto_cines_frame.domain.model.Producto

enum class CheckoutStep {
    SEATS,
    TICKETS,
    BAR,
    SUMMARY,
    PAYMENT
}

data class SeatPosition(
    val row: Int,
    val column: Int
)

typealias SeatMatrix = List<List<Boolean?>>

data class TicketSelection(
    val adulto: Int = 0,
    val nino: Int = 0,
    val senior: Int = 0
) {
    fun total(): Int = adulto + nino + senior

    fun totalPrice(): Float = adulto * 8.5f + nino * 6.0f + senior * 7.0f
}

data class CartProduct(
    val producto: Producto,
    val cantidad: Int = 0
) {
    fun lineTotal(): Float = producto.precio * cantidad
}

data class PaymentFormData(
    val holder: String = "",
    val cardNumber: String = "",
    val expiry: String = "",
    val cvv: String = "",
    val email: String = "",
    val confirmEmail: String = ""
)

fun nextStep(step: CheckoutStep): CheckoutStep {
    return when (step) {
        CheckoutStep.SEATS -> CheckoutStep.TICKETS
        CheckoutStep.TICKETS -> CheckoutStep.BAR
        CheckoutStep.BAR -> CheckoutStep.SUMMARY
        CheckoutStep.SUMMARY -> CheckoutStep.PAYMENT
        CheckoutStep.PAYMENT -> CheckoutStep.PAYMENT
    }
}

fun previousStep(step: CheckoutStep): CheckoutStep {
    return when (step) {
        CheckoutStep.SEATS -> CheckoutStep.SEATS
        CheckoutStep.TICKETS -> CheckoutStep.SEATS
        CheckoutStep.BAR -> CheckoutStep.TICKETS
        CheckoutStep.SUMMARY -> CheckoutStep.BAR
        CheckoutStep.PAYMENT -> CheckoutStep.SUMMARY
    }
}