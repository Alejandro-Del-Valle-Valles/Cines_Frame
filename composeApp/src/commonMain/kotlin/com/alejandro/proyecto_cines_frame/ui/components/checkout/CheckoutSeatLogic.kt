package com.alejandro.proyecto_cines_frame.ui.components.checkout

import com.alejandro.proyecto_cines_frame.domain.model.HoldToken
import kotlinx.datetime.LocalDateTime

const val DEFAULT_SEATS_PER_ROW = 10

fun buildSeatMatrixFromCapacity(
    capacity: Int,
    seatsPerRow: Int = DEFAULT_SEATS_PER_ROW
): SeatMatrix {
    if (capacity <= 0 || seatsPerRow <= 0) return emptyList()

    val rowsCount = (capacity + seatsPerRow - 1) / seatsPerRow
    val middleRow = rowsCount / 2

    return (0 until rowsCount - 1).map { rowIndex ->
        buildRowWithAisle(seatsPerRow, rowIndex == middleRow)
    }
}

fun toSeatPositionOrNull(
    fila: Int,
    butaca: Int,
    seatMatrix: SeatMatrix
): SeatPosition? {
    if (fila <= 0 || butaca <= 0) return null

    val rowIndex = fila - 1
    val row = seatMatrix.getOrNull(rowIndex) ?: return null
    var counter = 0

    row.forEachIndexed { colIndex, cell ->
        if (cell != null) {
            counter++
            if (counter == butaca) {
                return SeatPosition(row = rowIndex, column = colIndex)
            }
        }
    }

    return null
}

fun toSeatNumberOrNull(
    seatMatrix: SeatMatrix,
    position: SeatPosition
): Int? {
    val row = seatMatrix.getOrNull(position.row) ?: return null
    if (position.column !in row.indices) return null
    if (row[position.column] == null) return null

    var counter = 0
    row.forEachIndexed { colIndex, cell ->
        if (cell != null) {
            counter++
            if (colIndex == position.column) {
                return counter
            }
        }
    }

    return null
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


private fun buildRowWithAisle(seatsPerRow: Int, hasAisle: Boolean): List<Boolean?> {
    if (seatsPerRow <= 0) return emptyList()

    return if (hasAisle) {
        MutableList(seatsPerRow) { null }
    } else {
        MutableList(seatsPerRow) { true }
    }
}
