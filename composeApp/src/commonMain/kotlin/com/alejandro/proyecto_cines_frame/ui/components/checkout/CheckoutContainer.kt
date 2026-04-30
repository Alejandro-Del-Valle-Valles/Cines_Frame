package com.alejandro.proyecto_cines_frame.ui.components.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.session.SessionManager
import com.alejandro.proyecto_cines_frame.data.remote.dto.*
import com.alejandro.proyecto_cines_frame.domain.extension.toFirstUiMessagePerField
import com.alejandro.proyecto_cines_frame.domain.model.Compra
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.domain.model.TipoEntrada
import com.alejandro.proyecto_cines_frame.domain.validation.CheckoutPaymentValidator
import com.alejandro.proyecto_cines_frame.ui.theme.SurfaceDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import kotlinx.coroutines.launch

@Composable
fun CheckoutContainer(
    session: Sesion,
    state: CheckoutState,
    seatMatrix: SeatMatrix,
    remainingSeconds: Long,
    tiposEntrada: List<TipoEntrada>,
    products: List<CartProduct>,
    onProductsChange: (List<CartProduct>) -> Unit,
    onCancelCheckout: () -> Unit,
    onPurchaseCompleted: () -> Unit,
    onPreviousStep: () -> Unit,
    onSeatClick: (SeatPosition) -> Unit,
    onContinue: () -> Unit,
    onPerformPayment: suspend (CompraDTO) -> ApiResult<Compra>,
    holdTokenString: String
) {
    val authState by SessionManager.state.collectAsState()
    val sessionEmail = authState.cuenta?.usuario?.correo.orEmpty()
    val isAuthenticated = authState.isAuthenticated && sessionEmail.isNotBlank()

    var tickets by remember { mutableStateOf(TipoEntradaSelection()) }
    var paymentFormData by remember { mutableStateOf(PaymentFormData()) }
    var paymentFieldErrors by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    var paymentDone by remember { mutableStateOf(false) }
    var isPaying by remember { mutableStateOf(false) }
    var generalPaymentError by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(isAuthenticated, sessionEmail) {
        if (isAuthenticated) {
            paymentFormData = paymentFormData.copy(
                email = sessionEmail,
                confirmEmail = sessionEmail
            )
        }
    }

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
                CheckoutHeaderTop(
                    session = session,
                    remainingSeconds = remainingSeconds,
                    onBack = onCancelCheckout
                )

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
                                tiposEntrada = tiposEntrada,
                                tickets = tickets,
                                onChange = { tickets = it }
                            )
                        }

                        CheckoutStep.BAR -> {
                            BarStep(
                                products = products,
                                onUpdate = onProductsChange
                            )
                        }

                        CheckoutStep.SUMMARY -> {
                            SummaryStep(
                                movie = session.pelicula.nombre,
                                seats = state.selectedSeats.map {
                                    "Fila ${it.row + 1} · Butaca ${it.column + 1}"
                                },
                                tiposEntrada = tiposEntrada,
                                tickets = tickets,
                                products = products
                            )
                        }

                        CheckoutStep.PAYMENT -> {
                            PaymentFormStep(
                                compactLayout = compactLayout,
                                formData = paymentFormData,
                                fieldErrors = paymentFieldErrors,
                                generalError = generalPaymentError,
                                showEmailFields = !isAuthenticated,
                                sessionEmail = sessionEmail,
                                onHolderChange = {
                                    paymentFormData = paymentFormData.copy(holder = it)
                                    paymentFieldErrors = paymentFieldErrors - "holder"
                                },
                                onCardChange = {
                                    paymentFormData = paymentFormData.copy(cardNumber = it.filter(Char::isDigit))
                                    paymentFieldErrors = paymentFieldErrors - "cardNumber"
                                },
                                onExpiryChange = {
                                    paymentFormData = paymentFormData.copy(expiry = it)
                                    paymentFieldErrors = paymentFieldErrors - "expiry"
                                },
                                onCvvChange = {
                                    paymentFormData = paymentFormData.copy(cvv = it.filter(Char::isDigit).take(3))
                                    paymentFieldErrors = paymentFieldErrors - "cvv"
                                },
                                onEmailChange = {
                                    paymentFormData = paymentFormData.copy(email = it)
                                    paymentFieldErrors = paymentFieldErrors - "email"
                                },
                                onConfirmEmailChange = {
                                    paymentFormData = paymentFormData.copy(confirmEmail = it)
                                    paymentFieldErrors = paymentFieldErrors - "confirmEmail"
                                },
                                onBack = onPreviousStep,
                                onPay = {
                                    val errors = CheckoutPaymentValidator
                                        .validate(
                                            holder = paymentFormData.holder,
                                            cardNumber = paymentFormData.cardNumber,
                                            expiry = paymentFormData.expiry,
                                            cvv = paymentFormData.cvv,
                                            email = if (isAuthenticated) sessionEmail else paymentFormData.email,
                                            confirmEmail = if (isAuthenticated) sessionEmail else paymentFormData.confirmEmail,
                                            requireEmail = !isAuthenticated
                                        )
                                        .toFirstUiMessagePerField()

                                    paymentFieldErrors = errors

                                    if (errors.isEmpty()) {
                                        scope.launch {
                                            isPaying = true
                                            generalPaymentError = null

                                            // Construir CompraDTO
                                            val compraDto = buildCompraDto(
                                                email = if (isAuthenticated) sessionEmail else paymentFormData.email,
                                                holdToken = holdTokenString,
                                                session = session,
                                                selectedSeats = state.selectedSeats,
                                                tiposEntrada = tiposEntrada,
                                                tickets = tickets,
                                                products = products
                                            )

                                            when (val result = onPerformPayment(compraDto)) {
                                                is ApiResult.Success -> {
                                                    paymentDone = true
                                                }
                                                is ApiResult.Error -> {
                                                    //paymentDone = true
                                                    generalPaymentError = "No se ha podido completar la compra. Inténtelo de nuevo."
                                                }
                                            }
                                            isPaying = false
                                        }
                                    }
                                }
                            )
                        }
                    }
                }

                if (state.step != CheckoutStep.PAYMENT) {
                    CheckoutBottomBar(
                        step = state.step,
                        compactLayout = compactLayout,
                        onExit = onCancelCheckout,
                        onPrevious = onPreviousStep,
                        onContinue = onContinue,
                        continueEnabled = when (state.step) {
                            CheckoutStep.SEATS -> state.selectedSeats.isNotEmpty()
                            CheckoutStep.TICKETS -> {
                                tickets.total() == state.selectedSeats.size &&
                                    state.selectedSeats.isNotEmpty() &&
                                    tiposEntrada.isNotEmpty()
                            }
                            CheckoutStep.BAR -> true
                            CheckoutStep.SUMMARY -> true
                            else -> true
                        }
                    )
                }
            }
        }

        if (isPaying) {
            Box(
                modifier = Modifier.matchParentSize().background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = TextWhite)
            }
        }
    }

    if (paymentDone) {
        PaymentStep(
            onClose = {
                onPurchaseCompleted()
            }
        )
    }
}

private fun buildCompraDto(
    email: String,
    holdToken: String,
    session: Sesion,
    selectedSeats: Set<SeatPosition>,
    tiposEntrada: List<TipoEntrada>,
    tickets: TipoEntradaSelection,
    products: List<CartProduct>
): CompraDTO {
    val lineas = mutableListOf<LineaCompraDTO>()
    var numeroLinea = 1

    val orderedSeats = selectedSeats
        .sortedWith(compareBy<SeatPosition> { it.row }.thenBy { it.column })
    val selectedTipos = tickets.toEntradaTipos(tiposEntrada)

    // Entradas
    orderedSeats.zip(selectedTipos).forEach { (seat, tipo) ->
        lineas.add(
            LineaCompraEntradaDTO(
                numero = numeroLinea++,
                entrada = EntradaDTO(
                    sesion = SesionDTO(
                        numSala = session.numSala,
                        tresD = session.tresD,
                        vose = session.vose,
                        peliculaId = session.pelicula.id,
                        horario = session.horario.toString()
                    ),
                    numFila = seat.row + 1,
                    numButaca = seat.column + 1,
                    tipo = TipoEntradaDTO(
                        id = tipo.id,
                        nombre = tipo.nombre,
                        descripcion = tipo.descripcion,
                        precio = tipo.precio
                    )
                )
            )
        )
    }

    // Productos
    products.filter { it.cantidad > 0 }.forEach { cartProd ->
        repeat(cartProd.cantidad) {
            lineas.add(
                LineaCompraProductoCreateDTO(
                    numero = numeroLinea++,
                    nombreProducto = cartProd.producto.nombre
                )
            )
        }
    }

    return CompraDTO(
        correo = email,
        holdToken = holdToken,
        lineasCompra = lineas
    )
}