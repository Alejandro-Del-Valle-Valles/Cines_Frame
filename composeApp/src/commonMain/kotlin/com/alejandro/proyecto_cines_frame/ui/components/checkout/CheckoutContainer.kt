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
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorCodigoDescuentoApi
import com.alejandro.proyecto_cines_frame.data.remote.client.HttpClientFactory
import com.alejandro.proyecto_cines_frame.data.remote.dto.*
import com.alejandro.proyecto_cines_frame.data.repository.CodigoDescuentoRepositoryImpl
import com.alejandro.proyecto_cines_frame.domain.enums.CuentaRol
import com.alejandro.proyecto_cines_frame.domain.extension.toFirstUiMessagePerField
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento
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
    val isEmployee = authState.cuenta?.rol == CuentaRol.EMPLEADO

    var tickets by remember { mutableStateOf(TipoEntradaSelection()) }
    var paymentFormData by remember { mutableStateOf(PaymentFormData()) }
    var paymentFieldErrors by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var discountCode by remember { mutableStateOf("") }
    var appliedDiscount by remember { mutableStateOf<CodigoDescuento?>(null) }
    var discountFieldError by remember { mutableStateOf<String?>(null) }
    var isApplyingDiscount by remember { mutableStateOf(false) }

    var paymentDone by remember { mutableStateOf(false) }
    var isPaying by remember { mutableStateOf(false) }
    var generalPaymentError by remember { mutableStateOf<String?>(null) }

    val codigoDescuentoRepository = remember {
        CodigoDescuentoRepositoryImpl(
            api = KtorCodigoDescuentoApi(HttpClientFactory.create())
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(isAuthenticated, sessionEmail) {
        if (isAuthenticated) {
            paymentFormData = paymentFormData.copy(
                email = sessionEmail,
                confirmEmail = sessionEmail
            )
        }
    }

    LaunchedEffect(session.numSala, session.pelicula.id, session.horario) {
        discountCode = ""
        appliedDiscount = null
        discountFieldError = null
        isApplyingDiscount = false
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
                                seats = state.selectedSeats.mapNotNull { seat ->
                                    val seatNumber = toSeatNumberOrNull(seatMatrix, seat) ?: return@mapNotNull null
                                    "Fila ${seat.row + 1} · Butaca $seatNumber"
                                },
                                tiposEntrada = tiposEntrada,
                                tickets = tickets,
                                products = products,
                                discountCode = discountCode,
                                appliedDiscount = appliedDiscount,
                                discountError = discountFieldError,
                                isApplyingDiscount = isApplyingDiscount,
                                onDiscountCodeChange = {
                                    discountCode = it
                                    discountFieldError = null
                                },
                                onApplyDiscount = onApplyDiscount@{
                                    val trimmedCode = discountCode.trim()

                                    if (trimmedCode.isBlank()) {
                                        appliedDiscount = null
                                        discountCode = ""
                                        discountFieldError = null
                                        return@onApplyDiscount
                                    }

                                    scope.launch {
                                        isApplyingDiscount = true
                                        discountFieldError = null

                                        when (val result = codigoDescuentoRepository.getByCodigo(trimmedCode)) {
                                            is ApiResult.Success -> {
                                                val discount = result.data

                                                if (!discount.activo) {
                                                    discountFieldError = "El código de descuento está desactivado."
                                                } else {
                                                    appliedDiscount = discount
                                                    discountCode = discount.codigo
                                                }
                                            }

                                            is ApiResult.Error -> {
                                                discountFieldError = when (result.error) {
                                                    is com.alejandro.proyecto_cines_frame.core.error.AppError.NotFound -> "El código de descuento no existe."
                                                    is com.alejandro.proyecto_cines_frame.core.error.AppError.Conflict -> "El código de descuento no está disponible."
                                                    is com.alejandro.proyecto_cines_frame.core.error.AppError.Network -> "Error de conexión."
                                                    is com.alejandro.proyecto_cines_frame.core.error.AppError.Server -> "Error del servidor."
                                                    is com.alejandro.proyecto_cines_frame.core.error.AppError.Unauthorized -> "No autorizado."
                                                    is com.alejandro.proyecto_cines_frame.core.error.AppError.Forbidden -> "No tienes permisos para usar este código."
                                                    is com.alejandro.proyecto_cines_frame.core.error.AppError.Validation -> "El código de descuento no es válido."
                                                    else -> "Ha ocurrido un error inesperado."
                                                }
                                            }
                                        }

                                        isApplyingDiscount = false
                                    }
                                }
                            )
                        }

                        CheckoutStep.PAYMENT -> {
                            if (isEmployee) {
                                // Employee payment form
                                EmployeePaymentFormStep(
                                    compactLayout = compactLayout,
                                    formData = paymentFormData,
                                    fieldErrors = paymentFieldErrors,
                                    generalError = generalPaymentError,
                                    onEmailChange = {
                                        paymentFormData = paymentFormData.copy(email = it)
                                        paymentFieldErrors = paymentFieldErrors - "email"
                                    },
                                    onConfirmEmailChange = {
                                        paymentFormData = paymentFormData.copy(confirmEmail = it)
                                        paymentFieldErrors = paymentFieldErrors - "confirmEmail"
                                    },
                                    onBack = onPreviousStep,
                                    onPayCash = {
                                        processEmployeePayment(
                                            formData = paymentFormData,
                                            scope = scope,
                                            onFieldErrors = { paymentFieldErrors = it },
                                            onGeneralError = { generalPaymentError = it },
                                            onPaying = { isPaying = it },
                                            buildCompra = {
                                                buildCompraDto(
                                                    email = paymentFormData.email,
                                                    holdToken = holdTokenString,
                                                    session = session,
                                                    seatMatrix = seatMatrix,
                                                    selectedSeats = state.selectedSeats,
                                                    tiposEntrada = tiposEntrada,
                                                    tickets = tickets,
                                                    products = products,
                                                    codigoDescuento = appliedDiscount?.codigo
                                                )
                                            },
                                            onPaymentResult = { result ->
                                                when (result) {
                                                    is ApiResult.Success -> paymentDone = true
                                                    is ApiResult.Error -> generalPaymentError = "No se ha podido completar la compra. Inténtelo de nuevo."
                                                }
                                            },
                                            onPerformPayment = onPerformPayment
                                        )
                                    },
                                    onPayCard = {
                                        processEmployeePayment(
                                            formData = paymentFormData,
                                            scope = scope,
                                            onFieldErrors = { paymentFieldErrors = it },
                                            onGeneralError = { generalPaymentError = it },
                                            onPaying = { isPaying = it },
                                            buildCompra = {
                                                buildCompraDto(
                                                    email = paymentFormData.email,
                                                    holdToken = holdTokenString,
                                                    session = session,
                                                    seatMatrix = seatMatrix,
                                                    selectedSeats = state.selectedSeats,
                                                    tiposEntrada = tiposEntrada,
                                                    tickets = tickets,
                                                    products = products,
                                                    codigoDescuento = appliedDiscount?.codigo
                                                )
                                            },
                                            onPaymentResult = { result ->
                                                when (result) {
                                                    is ApiResult.Success -> paymentDone = true
                                                    is ApiResult.Error -> generalPaymentError = "No se ha podido completar la compra. Inténtelo de nuevo."
                                                }
                                            },
                                            onPerformPayment = onPerformPayment
                                        )
                                    }
                                )
                            } else {
                                // ...existing code...
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
                                                    seatMatrix = seatMatrix,
                                                    selectedSeats = state.selectedSeats,
                                                    tiposEntrada = tiposEntrada,
                                                    tickets = tickets,
                                                    products = products,
                                                    codigoDescuento = appliedDiscount?.codigo
                                                )

                                                when (onPerformPayment(compraDto)) {
                                                    is ApiResult.Success -> {
                                                        paymentDone = true
                                                    }
                                                    is ApiResult.Error -> {
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
    seatMatrix: SeatMatrix,
    selectedSeats: Set<SeatPosition>,
    tiposEntrada: List<TipoEntrada>,
    tickets: TipoEntradaSelection,
    products: List<CartProduct>,
    codigoDescuento: String? = null
): CompraDTO {
    val lineas = mutableListOf<LineaCompraDTO>()
    var numeroLinea = 1

    val orderedSeats = selectedSeats
        .sortedWith(compareBy<SeatPosition> { it.row }.thenBy { it.column })
    val selectedTipos = tickets.toEntradaTipos(tiposEntrada)

    // Entradas
    orderedSeats.zip(selectedTipos).forEach { (seat, tipo) ->
        val seatNumber = toSeatNumberOrNull(seatMatrix, seat) ?: return@forEach
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
                    numButaca = seatNumber,
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
        lineasCompra = lineas,
        codigoDescuento = codigoDescuento
    )
}

private fun processEmployeePayment(
    formData: PaymentFormData,
    scope: kotlinx.coroutines.CoroutineScope,
    onFieldErrors: (Map<String, String>) -> Unit,
    onGeneralError: (String?) -> Unit,
    onPaying: (Boolean) -> Unit,
    buildCompra: () -> CompraDTO,
    onPaymentResult: (ApiResult<Compra>) -> Unit,
    onPerformPayment: suspend (CompraDTO) -> ApiResult<Compra>
) {
    // Validate emails
    val errors = mutableMapOf<String, String>()

    if (formData.email.isBlank()) {
        errors["email"] = "El correo es requerido"
    } else if (!isValidEmail(formData.email)) {
        errors["email"] = "Correo inválido"
    }

    if (formData.confirmEmail.isBlank()) {
        errors["confirmEmail"] = "La confirmación de correo es requerida"
    } else if (formData.email != formData.confirmEmail) {
        errors["confirmEmail"] = "Los correos no coinciden"
    }

    onFieldErrors(errors)

    if (errors.isEmpty()) {
        scope.launch {
            onPaying(true)
            onGeneralError(null)

            val compraDto = buildCompra()
            val result = onPerformPayment(compraDto)
            onPaymentResult(result)
            onPaying(false)
        }
    }
}

private fun isValidEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
    return emailPattern.matches(email)
}
