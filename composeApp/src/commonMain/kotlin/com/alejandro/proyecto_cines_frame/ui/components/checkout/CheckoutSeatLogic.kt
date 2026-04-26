package com.alejandro.proyecto_cines_frame.ui.components.checkout

import com.alejandro.proyecto_cines_frame.domain.model.HoldToken
import kotlinx.datetime.LocalDateTime

const val DEFAULT_SEATS_PER_ROW = 10

fun buildSeatMatrixFromCapacity(
    capacity: Int,
    seatsPerRow: Int = DEFAULT_SEATS_PER_ROW
): SeatMatrix {
    if (capacity <= 0 || seatsPerRow <= 0) return emptyList()

    val fullRows = capacity / seatsPerRow
    val lastRowSeats = capacity % seatsPerRow

    val rows = MutableList(fullRows) {
        List<Boolean?>(seatsPerRow) { true }
    }

    if (lastRowSeats > 0) {
        rows += List<Boolean?>(lastRowSeats) { true }
    }

    return rows
}

fun toSeatPositionOrNull(fila: Int, butaca: Int): SeatPosition? {
    if (fila <= 0 || butaca <= 0) return null
    return SeatPosition(row = fila - 1, column = butaca - 1)
}

fun applyUnavailableSeats(
    baseMatrix: SeatMatrix,
    unavailableSeats: Set<SeatPosition>
): SeatMatrix {
    if (unavailableSeats.isEmpty()) return baseMatrix

    return baseMatrix.mapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, cell ->
            if (cell == true && SeatPosition(rowIndex, colIndex) in unavailableSeats) false else cell
        }
    }
}

fun blockSeat(selectedSeats: Set<SeatPosition>, seat: SeatPosition): Set<SeatPosition> = selectedSeats + seat

fun unblockSeat(selectedSeats: Set<SeatPosition>, seat: SeatPosition): Set<SeatPosition> = selectedSeats - seat

fun canUseHoldToken(token: HoldToken?, now: LocalDateTime): Boolean {
    return token != null && !token.isExpired(now)
}

