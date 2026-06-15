package com.alejandro.proyecto_cines_frame.ui.components.checkout.paraDescuento

import com.alejandro.proyecto_cines_frame.domain.enums.DescuentoCondicion
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento

data class DiscountResult(
    val amount: Float,
    val appliedCode: String?
)

fun calculateDiscount(
    descuento: CodigoDescuento?,
    ticketsTotal: Float,
    barTotal: Float
): DiscountResult {

    if (descuento == null || !descuento.activo) {
        return DiscountResult(
            amount = 0f,
            appliedCode = null
        )
    }

    val percentage = descuento.porcentajeDescuento / 100f

    val discountAmount = when (descuento.condicion) {

        DescuentoCondicion.COMIDA ->
            barTotal * percentage

        DescuentoCondicion.PELICULA ->
            ticketsTotal * percentage

        DescuentoCondicion.TODOS ->
            (ticketsTotal + barTotal) * percentage
    }

    return DiscountResult(
        amount = discountAmount,
        appliedCode = descuento.codigo
    )
}