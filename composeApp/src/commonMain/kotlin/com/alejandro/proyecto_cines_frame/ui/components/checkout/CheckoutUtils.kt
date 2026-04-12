package com.alejandro.proyecto_cines_frame.ui.components.checkout

fun nextStep(step: CheckoutStep): CheckoutStep {
    return when (step) {
        CheckoutStep.SEATS -> CheckoutStep.TICKETS
        CheckoutStep.TICKETS -> CheckoutStep.BAR
        CheckoutStep.BAR -> CheckoutStep.SUMMARY
        CheckoutStep.SUMMARY -> CheckoutStep.SUMMARY
    }
}