package com.alejandro.proyecto_cines_frame.ui.components.checkout

import com.alejandro.proyecto_cines_frame.domain.model.Producto
import com.alejandro.proyecto_cines_frame.domain.model.TipoEntrada

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

data class TipoEntradaSelection(
    val cantidades: Map<Int, Int> = emptyMap()
) {
    fun total(): Int = cantidades.values.sum()

    fun cantidadFor(tipoId: Int): Int = cantidades[tipoId] ?: 0

    fun update(tipoId: Int, cantidad: Int): TipoEntradaSelection {
        val next = cantidades.toMutableMap()
        if (cantidad <= 0) {
            next.remove(tipoId)
        } else {
            next[tipoId] = cantidad
        }
        return copy(cantidades = next)
    }

    fun totalPrice(tipos: List<TipoEntrada>): Float {
        val tiposById = tipos.associateBy { it.id }
        return cantidades.entries.sumOf { (id, qty) ->
            val precio = tiposById[id]?.precio ?: 0f
            (precio * qty).toDouble()
        }.toFloat()
    }

    fun toEntradaTipos(tipos: List<TipoEntrada>): List<TipoEntrada> {
        val tiposById = tipos.associateBy { it.id }
        return cantidades.entries.flatMap { (id, qty) ->
            val tipo = tiposById[id] ?: return@flatMap emptyList()
            List(qty) { tipo }
        }
    }
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