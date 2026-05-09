package com.alejandro.proyecto_cines_frame.ui.components.checkout

import com.alejandro.proyecto_cines_frame.domain.model.HoldToken
import kotlinx.datetime.LocalDateTime

const val DEFAULT_SEATS_PER_ROW = 10

fun buildSeatMatrixFromCapacity(
    capacity: Int,
    seatsPerRow: Int = DEFAULT_SEATS_PER_ROW
): SeatMatrix {
    if (capacity <= 0 || seatsPerRow <= 0) return emptyList()

    val rowSeatCounts = buildRowSeatCounts(capacity, seatsPerRow)
    return rowSeatCounts.map { seatsInRow ->
        buildRowWithCentralAisle(seatsInRow, seatsPerRow)
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

private fun buildRowSeatCounts(capacity: Int, seatsPerRow: Int): List<Int> {
    if (capacity <= 0) return emptyList()

    val rowsCount = (capacity + seatsPerRow - 1) / seatsPerRow
    val totalCapacity = rowsCount * seatsPerRow
    var deficit = totalCapacity - capacity

    val rowCounts = MutableList(rowsCount) { seatsPerRow }
    if (deficit == 0) return rowCounts

    val rampRows = minOf(5, rowsCount)
    val minSeats = maxOf(2, seatsPerRow - 6)
    val maxReduction = seatsPerRow - minSeats

    val weights = (0 until rampRows).map { rampRows - it }
    val weightSum = weights.sum().coerceAtLeast(1)
    val reductions = IntArray(rampRows)

    for (i in 0 until rampRows) {
        if (deficit <= 0) break
        val proposed = (deficit * weights[i]) / weightSum
        val applied = proposed.coerceIn(0, maxReduction)
        reductions[i] = applied
    }

    var remaining = deficit - reductions.sum()
    var guard = 0
    while (remaining > 0 && guard < rampRows * (maxReduction + 1)) {
        for (i in 0 until rampRows) {
            if (remaining == 0) break
            if (reductions[i] < maxReduction) {
                reductions[i]++
                remaining--
            }
        }
        guard++
    }

    for (i in 0 until rampRows) {
        rowCounts[i] = (rowCounts[i] - reductions[i]).coerceAtLeast(minSeats)
    }

    if (remaining > 0) {
        var row = rampRows
        while (remaining > 0 && row < rowsCount) {
            val available = rowCounts[row] - minSeats
            if (available > 0) {
                val reduce = minOf(available, remaining)
                rowCounts[row] -= reduce
                remaining -= reduce
            }
            row++
        }
    }

    return rowCounts
}

private fun buildRowWithCentralAisle(seatsInRow: Int, seatsPerRow: Int): List<Boolean?> {
    if (seatsInRow <= 0) return emptyList()

    val totalColumns = seatsPerRow + 1
    val aisleIndex = totalColumns / 2
    val leftArea = aisleIndex
    val rightArea = totalColumns - aisleIndex - 1

    val initialLeftSeats = (seatsInRow + 1) / 2
    val initialRightSeats = seatsInRow - initialLeftSeats

    val leftSeats = initialLeftSeats.coerceIn(0, leftArea)
    val rightSeats = (seatsInRow - leftSeats).coerceIn(0, rightArea)

    val adjustedLeftSeats = (seatsInRow - rightSeats).coerceIn(0, leftArea)

    val row = MutableList<Boolean?>(totalColumns) { null }

    val leftStart = aisleIndex - adjustedLeftSeats
    repeat(adjustedLeftSeats) { offset ->
        row[leftStart + offset] = true
    }

    val rightStart = aisleIndex + 1
    repeat(rightSeats) { offset ->
        row[rightStart + offset] = true
    }

    return row
}
