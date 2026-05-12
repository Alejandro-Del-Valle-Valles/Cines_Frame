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

            maxWidth < 260.dp ->
                18.dp

            maxWidth < 320.dp ->
                22.dp

            maxWidth < 420.dp ->
                26.dp

            maxWidth < 700.dp ->
                30.dp

            else ->
                40.dp
        }

        val spacing = when {

            maxWidth < 320.dp ->
                2.dp

            maxWidth < 420.dp ->
                4.dp

            else ->
                6.dp
        }

        Column(

            verticalArrangement =
                Arrangement.spacedBy(spacing)
        ) {

            seatMatrix.forEachIndexed { rowIndex, row ->

                Row(

                    horizontalArrangement =
                        Arrangement.spacedBy(spacing)
                ) {

                    row.forEachIndexed { colIndex, cell ->

                        when (cell) {

                            null -> {

                                Spacer(
                                    modifier =
                                        Modifier.size(seatSize)
                                )
                            }

                            true -> {

                                val pos =
                                    SeatPosition(
                                        row = rowIndex,
                                        column = colIndex
                                    )

                                CinemaSeat(
                                    isSelected =
                                        selectedSeats.contains(pos),

                                    isReserved = false,

                                    seatSize = seatSize,

                                    onClick = {
                                        onSeatClick(pos)
                                    }
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