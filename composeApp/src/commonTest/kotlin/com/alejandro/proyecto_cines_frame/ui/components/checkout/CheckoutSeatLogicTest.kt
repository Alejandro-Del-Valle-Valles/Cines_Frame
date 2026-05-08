package com.alejandro.proyecto_cines_frame.ui.components.checkout

import com.alejandro.proyecto_cines_frame.domain.model.HoldToken
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CheckoutSeatLogicTest {

    @Test
    fun buildSeatMatrixFromCapacity_createsAisleLayoutAndPreservesCapacity() {
        val matrix = buildSeatMatrixFromCapacity(capacity = 23)

        assertEquals(3, matrix.size)
        assertTrue(matrix.all { it.size == DEFAULT_SEATS_PER_ROW + 1 })
        assertEquals(23, matrix.sumOf { row -> row.count { it != null } })
        assertTrue(matrix.flatten().any { it == null })
    }

    @Test
    fun applyUnavailableSeats_marksOccupiedAndBlockedAsFalse() {
        val base = buildSeatMatrixFromCapacity(capacity = DEFAULT_SEATS_PER_ROW * 3)
        val unavailable = setOf(
            SeatPosition(0, 0),
            SeatPosition(1, 2)
        )

        val updated = applyUnavailableSeats(base, unavailable)

        assertEquals(false, updated[0][0])
        assertEquals(false, updated[1][2])
        assertEquals(true, updated[2][2])
    }

    @Test
    fun toSeatPositionOrNull_mapsSeatNumberSkippingAisles() {
        val matrix = buildSeatMatrixFromCapacity(capacity = 10)

        assertEquals(SeatPosition(0, 0), toSeatPositionOrNull(fila = 1, butaca = 1, seatMatrix = matrix))
        assertEquals(SeatPosition(0, 6), toSeatPositionOrNull(fila = 1, butaca = 6, seatMatrix = matrix))
        assertNull(toSeatPositionOrNull(fila = 0, butaca = 2, seatMatrix = matrix))
    }

    @Test
    fun toSeatNumberOrNull_mapsPositionToSeatNumberSkippingAisles() {
        val matrix = buildSeatMatrixFromCapacity(capacity = 10)

        assertEquals(6, toSeatNumberOrNull(matrix, SeatPosition(0, 6)))
        assertNull(toSeatNumberOrNull(matrix, SeatPosition(0, 5)))
    }

    @Test
    fun blockAndUnblockSeat_updatesSelection() {
        val seat = SeatPosition(0, 0)
        val blocked = blockSeat(emptySet(), seat)

        assertTrue(seat in blocked)

        val unblocked = unblockSeat(blocked, seat)
        assertFalse(seat in unblocked)
    }

    @Test
    fun canUseHoldToken_returnsFalseWhenExpired() {
        val token = HoldToken(
            holdToken = "abc",
            expira = LocalDateTime.parse("2026-01-01T10:00:00")
        )

        assertTrue(canUseHoldToken(token, LocalDateTime.parse("2026-01-01T09:59:59")))
        assertFalse(canUseHoldToken(token, LocalDateTime.parse("2026-01-01T10:00:01")))
        assertFalse(canUseHoldToken(null, LocalDateTime.parse("2026-01-01T09:00:00")))
    }
}
