package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.BoxWithConstraints
import com.alejandro.proyecto_cines_frame.ui.components.common.CinemaSeat

@Composable
fun SeatMap(
    seatMatrix: SeatMatrix,
    selectedSeats: Set<SeatPosition>,
    onSeatClick: (SeatPosition) -> Unit
) {

    BoxWithConstraints {

        val seatSize = when {
            maxWidth < 400.dp -> 24.dp
            maxWidth < 800.dp -> 32.dp
            else -> 40.dp
        }

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

            seatMatrix.forEachIndexed { rowIndex, row ->

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                    row.forEachIndexed { colIndex, cell ->

                        when (cell) {

                            null -> Spacer(Modifier.size(seatSize))

                            true -> {
                                val pos = SeatPosition(rowIndex, colIndex)

                                CinemaSeat(
                                    isSelected = selectedSeats.contains(pos),
                                    isReserved = false,
                                    seatSize = seatSize,
                                    onClick = { onSeatClick(pos) }
                                )
                            }

                            false -> {
                                CinemaSeat(
                                    isSelected = false,
                                    isReserved = true,
                                    seatSize = seatSize,
                                    onClick = {}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}