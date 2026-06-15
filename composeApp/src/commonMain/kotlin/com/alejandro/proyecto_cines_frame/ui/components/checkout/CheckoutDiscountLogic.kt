package com.alejandro.proyecto_cines_frame.ui.components.checkout

import com.alejandro.proyecto_cines_frame.domain.enums.DescuentoCondicion
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento

data class CheckoutPricing(
    val ticketsTotal: Float,
    val barTotal: Float,
    val discountAmount: Float = 0f
) {
    val subtotal: Float = ticketsTotal + barTotal
    val finalTotal: Float = (subtotal - discountAmount).coerceAtLeast(0f)
}

fun calculateCheckoutPricing(
    ticketsTotal: Float,
    barTotal: Float,
    discount: CodigoDescuento? = null
): CheckoutPricing {
    val discountAmount = calculateDiscountAmount(ticketsTotal, barTotal, discount)
    return CheckoutPricing(
        ticketsTotal = ticketsTotal,
        barTotal = barTotal,
        discountAmount = discountAmount
    )
}

fun calculateDiscountAmount(
    ticketsTotal: Float,
    barTotal: Float,
    discount: CodigoDescuento?
): Float {
    val code = discount ?: return 0f
    val baseAmount = when (code.condicion) {
        DescuentoCondicion.COMIDA -> barTotal
        DescuentoCondicion.PELICULA -> ticketsTotal
        DescuentoCondicion.TODOS -> ticketsTotal + barTotal
    }.coerceAtLeast(0f)

    val percentage = code.porcentajeDescuento.coerceIn(0, 100)
    return (baseAmount * percentage / 100f).coerceAtLeast(0f)
}

fun DescuentoCondicion.toCheckoutTargetLabel(): String {
    return when (this) {
        DescuentoCondicion.COMIDA -> "Bar"
        DescuentoCondicion.PELICULA -> "Entradas"
        DescuentoCondicion.TODOS -> "Todo"
    }
}

fun CodigoDescuento.targetLabel(): String = condicion.toCheckoutTargetLabel()