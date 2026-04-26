package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.error.AppError
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorSesionApi
import com.alejandro.proyecto_cines_frame.data.remote.client.HttpClientFactory
import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldButacaRequest
import com.alejandro.proyecto_cines_frame.data.repository.SesionRepositoryImpl
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.domain.repository.SesionRepository
import com.alejandro.proyecto_cines_frame.ui.components.checkout.*
import com.alejandro.proyecto_cines_frame.ui.components.footer.Footer
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@Composable
fun CheckoutScreen(
    session: Sesion,
    salaCapacity: Int,
    onBack: () -> Unit,
    sesionRepository: SesionRepository? = null
) {
    val repository = sesionRepository ?: remember {
        SesionRepositoryImpl(
            api = KtorSesionApi(HttpClientFactory.create())
        )
    }

    val horarioApi = remember(session) { session.horario.toString() }
    val timeZone = remember { TimeZone.currentSystemDefault() }
    val baseSeatMatrix = remember(salaCapacity) { buildSeatMatrixFromCapacity(salaCapacity) }
    val scope = rememberCoroutineScope()

    var state by remember { mutableStateOf(CheckoutState()) }
    var seatMatrix by remember(baseSeatMatrix) { mutableStateOf(baseSeatMatrix) }
    var holdToken by remember { mutableStateOf<com.alejandro.proyecto_cines_frame.domain.model.HoldToken?>(null) }
    var remainingSeconds by remember { mutableStateOf(0L) }
    var isLoadingCheckout by remember { mutableStateOf(true) }
    var isClosingCheckout by remember { mutableStateOf(false) }
    var checkoutMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(session.numSala, session.pelicula.id, horarioApi, salaCapacity) {
        isLoadingCheckout = true
        isClosingCheckout = false
        checkoutMessage = null
        holdToken = null
        state = CheckoutState()
        seatMatrix = baseSeatMatrix

        when (val holdTokenResult = repository.createHoldToken(session.numSala, session.pelicula.id, horarioApi)) {
            is ApiResult.Success -> {
                holdToken = holdTokenResult.data
                remainingSeconds = secondsUntilExpiry(holdTokenResult.data.expira.toInstant(timeZone).epochSeconds)
            }

            is ApiResult.Error -> {
                checkoutMessage = toCheckoutErrorMessage(holdTokenResult.error)
                isLoadingCheckout = false
                return@LaunchedEffect
            }
        }

        when (val butacasStatusResult = repository.butacasStatus(session.numSala, session.pelicula.id, horarioApi)) {
            is ApiResult.Success -> {
                val unavailableSeats = (butacasStatusResult.data.ocupadas + butacasStatusResult.data.bloqueadas)
                    .mapNotNull { toSeatPositionOrNull(it.fila, it.butaca) }
                    .toSet()
                seatMatrix = applyUnavailableSeats(baseSeatMatrix, unavailableSeats)
            }

            is ApiResult.Error -> {
                checkoutMessage = toCheckoutErrorMessage(butacasStatusResult.error)
            }
        }

        isLoadingCheckout = false
    }

    LaunchedEffect(holdToken?.expira) {
        val token = holdToken ?: return@LaunchedEffect

        while (true) {
            val newRemainingSeconds = secondsUntilExpiry(token.expira.toInstant(timeZone).epochSeconds)
            remainingSeconds = newRemainingSeconds

            if (newRemainingSeconds <= 0L) {
                state = state.copy(selectedSeats = emptySet())
                checkoutMessage = "El tiempo de bloqueo ha expirado. Vuelve a seleccionar la sesión."
                break
            }

            delay(1_000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoadingCheckout -> {
                    CircularProgressIndicator(color = TextWhite)
                }

                holdToken == null -> {
                    Text(
                        text = checkoutMessage ?: "No se pudo iniciar el checkout",
                        color = TextWhite
                    )
                }

                else -> {
                    val tokenUsable = canUseHoldToken(
                        token = holdToken,
                        now = Clock.System.now().toLocalDateTime(timeZone)
                    )

                    CheckoutContainer(
                        session = session,
                        state = state,
                        seatMatrix = seatMatrix,
                        remainingSeconds = remainingSeconds,
                        onCancelCheckout = {
                            if (!isClosingCheckout) {
                                isClosingCheckout = true
                                scope.launch {
                                    cleanupCheckoutAndExit(
                                        repository = repository,
                                        session = session,
                                        horarioApi = horarioApi,
                                        activeToken = holdToken,
                                        seatsToRelease = state.selectedSeats,
                                        releaseSelectedSeats = true,
                                        onCleanupError = { checkoutMessage = it }
                                    )
                                    onBack()
                                }
                            }
                        },
                        onPurchaseCompleted = {
                            if (!isClosingCheckout) {
                                isClosingCheckout = true
                                scope.launch {
                                    cleanupCheckoutAndExit(
                                        repository = repository,
                                        session = session,
                                        horarioApi = horarioApi,
                                        activeToken = holdToken,
                                        seatsToRelease = state.selectedSeats,
                                        releaseSelectedSeats = false,
                                        onCleanupError = { checkoutMessage = it }
                                    )
                                    onBack()
                                }
                            }
                        },
                        onPreviousStep = {
                            state = state.copy(step = previousStep(state.step))
                        },
                        onSeatClick = { clicked ->
                            if (!tokenUsable) {
                                checkoutMessage = "El tiempo para comprar ha expirado y no se pueden bloquear butacas."
                            } else {
                                val activeToken = holdToken
                                if (activeToken == null) {
                                    checkoutMessage = "No hay token activo para bloquear butacas."
                                } else {
                                    val request = HoldButacaRequest(
                                        token = activeToken.holdToken,
                                        fila = clicked.row + 1,
                                        butaca = clicked.column + 1
                                    )

                                    scope.launch {
                                        if (clicked in state.selectedSeats) {
                                            when (
                                                repository.releaseButaca(
                                                    numSala = session.numSala,
                                                    peliculaId = session.pelicula.id,
                                                    horario = horarioApi,
                                                    req = request
                                                )
                                            ) {
                                                is ApiResult.Success -> {
                                                    state = state.copy(
                                                        selectedSeats = unblockSeat(state.selectedSeats, clicked)
                                                    )
                                                }

                                                is ApiResult.Error -> {
                                                    checkoutMessage = "No se pudo desbloquear la butaca."
                                                }
                                            }
                                        } else {
                                            when (
                                                val holdSeatResult = repository.holdButaca(
                                                    numSala = session.numSala,
                                                    peliculaId = session.pelicula.id,
                                                    horario = horarioApi,
                                                    req = request
                                                )
                                            ) {
                                                is ApiResult.Success -> {
                                                    state = state.copy(
                                                        selectedSeats = blockSeat(state.selectedSeats, clicked)
                                                    )
                                                }

                                                is ApiResult.Error -> {
                                                    if (holdSeatResult.error is AppError.Conflict) {
                                                        seatMatrix = applyUnavailableSeats(seatMatrix, setOf(clicked))
                                                    }
                                                    checkoutMessage = toCheckoutErrorMessage(holdSeatResult.error)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        onContinue = {
                            if (!tokenUsable) {
                                checkoutMessage = "El token ha expirado. Debes volver a entrar en la sesión."
                            } else {
                                state = state.copy(step = nextStep(state.step))
                            }
                        }
                    )
                }
            }
        }

        if (!checkoutMessage.isNullOrBlank()) {
            Text(
                text = checkoutMessage.orEmpty(),
                color = TextWhite,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }

        Footer()
    }
}

private suspend fun cleanupCheckoutAndExit(
    repository: SesionRepository,
    session: Sesion,
    horarioApi: String,
    activeToken: com.alejandro.proyecto_cines_frame.domain.model.HoldToken?,
    seatsToRelease: Set<SeatPosition>,
    releaseSelectedSeats: Boolean,
    onCleanupError: (String) -> Unit
) {
    val token = activeToken ?: return

    if (releaseSelectedSeats && seatsToRelease.isNotEmpty()) {
        seatsToRelease.forEach { seat ->
            val result = repository.releaseButaca(
                numSala = session.numSala,
                peliculaId = session.pelicula.id,
                horario = horarioApi,
                req = HoldButacaRequest(
                    token = token.holdToken,
                    fila = seat.row + 1,
                    butaca = seat.column + 1
                )
            )

            if (result is ApiResult.Error) {
                onCleanupError("No se pudo liberar alguna butaca seleccionada.")
            }
        }
    }

    when (
        val releaseTokenResult = repository.releaseHoldToken(
            numSala = session.numSala,
            peliculaId = session.pelicula.id,
            horario = horarioApi,
            token = token.holdToken
        )
    ) {
        is ApiResult.Success -> Unit
        is ApiResult.Error -> onCleanupError("No se pudo liberar el token de reserva.")
    }
}

private fun secondsUntilExpiry(expiryEpochSeconds: Long): Long {
    val nowEpochSeconds = Clock.System.now().epochSeconds
    return expiryEpochSeconds - nowEpochSeconds
}

private fun toCheckoutErrorMessage(error: AppError): String =
    when (error) {
        is AppError.Network -> error.message ?: "No hay conexion con el servidor"
        is AppError.Unknown -> error.message ?: "Error desconocido"
        is AppError.Validation -> "No se pudo procesar la petición"
        is AppError.Unauthorized -> "No autorizado"
        is AppError.Forbidden -> "Acceso denegado"
        is AppError.NotFound -> "Sesion no encontrada"
        is AppError.Conflict -> "La butaca ya esta ocupada o bloqueada"
        is AppError.Server -> "Error del servidor (${error.code})"
    }
