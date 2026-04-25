package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Producto
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.theme.SurfaceDark

@Composable
fun CheckoutContainer(
    session: Sesion,
    state: CheckoutState,
    seatMatrix: SeatMatrix,
    onBack: () -> Unit,
    onPreviousStep: () -> Unit,
    onSeatClick: (SeatPosition) -> Unit,
    onContinue: () -> Unit
) {
    var tickets by remember { mutableStateOf(TicketSelection()) }

    var products by remember {
        mutableStateOf(
            listOf(
                CartProduct(Producto("Palomitas", 6.5f, 20)),
                CartProduct(Producto("Refresco", 4.0f, 18)),
                CartProduct(Producto("Nachos", 5.5f, 10)),
                CartProduct(Producto("Agua", 2.5f, 25))
            )
        )
    }

    var paymentDone by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 620.dp)
    ) {
        val compactLayout = maxWidth < 900.dp

        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                CheckoutHeaderTop(session, onBack)

                CheckoutStepperModern(
                    step = state.step,
                    compactLayout = compactLayout
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    when (state.step) {
                        CheckoutStep.SEATS -> {
                            CheckoutSeatSection(
                                seatMatrix = seatMatrix,
                                selectedSeats = state.selectedSeats,
                                onSeatClick = onSeatClick,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        CheckoutStep.TICKETS -> {
                            TicketsStep(
                                seatsSelected = state.selectedSeats.size,
                                tickets = tickets,
                                onChange = { tickets = it }
                            )
                        }

                        CheckoutStep.BAR -> {
                            BarStep(
                                products = products,
                                onUpdate = { products = it }
                            )
                        }

                        CheckoutStep.SUMMARY -> {
                            SummaryStep(
                                movie = session.pelicula.nombre,
                                seats = state.selectedSeats.map {
                                    "Fila ${it.row + 1} · Butaca ${it.column + 1}"
                                },
                                tickets = tickets,
                                products = products
                            )
                        }

                        CheckoutStep.PAYMENT -> {
                            PaymentFormStep(
                                compactLayout = compactLayout,
                                onBack = onPreviousStep,
                                onPay = {
                                    paymentDone = true
                                }
                            )
                        }
                    }
                }

                if (state.step != CheckoutStep.PAYMENT) {
                    CheckoutBottomBar(
                        step = state.step,
                        compactLayout = compactLayout,
                        onExit = onBack,
                        onPrevious = onPreviousStep,
                        onContinue = onContinue,
                        continueEnabled = when (state.step) {
                            CheckoutStep.SEATS -> state.selectedSeats.isNotEmpty()
                            CheckoutStep.TICKETS -> tickets.total() == state.selectedSeats.size && state.selectedSeats.isNotEmpty()
                            CheckoutStep.BAR -> true
                            CheckoutStep.SUMMARY -> true
                            CheckoutStep.PAYMENT -> false
                        }
                    )
                }
            }
        }
    }

    if (paymentDone) {
        PaymentStep(
            onClose = {
                paymentDone = false
                onBack()
            }
        )
    }
}