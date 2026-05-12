package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray

@Composable
fun CheckoutSeatSection(
    seatMatrix: SeatMatrix,
    selectedSeats: Set<SeatPosition>,
    onSeatClick: (SeatPosition) -> Unit,
    modifier: Modifier = Modifier
) {

    val verticalState = rememberScrollState()
    val horizontalState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    end = 14.dp,
                    bottom = 14.dp
                )
        ) {

            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalState)
                    .horizontalScroll(horizontalState),

                contentAlignment =
                    Alignment.TopCenter
            ) {

                Column(

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "PANTALLA",
                        color = TextGray
                    )

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    SeatMap(
                        seatMatrix = seatMatrix,
                        selectedSeats = selectedSeats,
                        onSeatClick = onSeatClick
                    )

                    Spacer(
                        modifier = Modifier.height(32.dp)
                    )
                }
            }

            VerticalScrollbar(

                adapter =
                    rememberScrollbarAdapter(verticalState),

                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(10.dp)
            )

            HorizontalScrollbar(

                adapter =
                    rememberScrollbarAdapter(horizontalState),

                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .height(10.dp)
            )
        }
    }
}