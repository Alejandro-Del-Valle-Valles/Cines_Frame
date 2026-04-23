package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.core.error.AppError
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.security.CredentialStoreFactory
import com.alejandro.proyecto_cines_frame.core.session.SessionManager
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorBanerApi
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorCuentaApi
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorSesionApi
import com.alejandro.proyecto_cines_frame.data.remote.client.HttpClientFactory
import com.alejandro.proyecto_cines_frame.data.repository.BanerRepositoryImpl
import com.alejandro.proyecto_cines_frame.data.repository.CuentaRepositoryImpl
import com.alejandro.proyecto_cines_frame.data.repository.SesionRepositoryImpl
import com.alejandro.proyecto_cines_frame.domain.repository.SesionRepository
import com.alejandro.proyecto_cines_frame.ui.components.banner.Banner
import com.alejandro.proyecto_cines_frame.ui.components.banner.OfflineBannerScreen
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.MovieSection
import com.alejandro.proyecto_cines_frame.ui.components.filter.*
import com.alejandro.proyecto_cines_frame.ui.components.footer.Footer
import com.alejandro.proyecto_cines_frame.ui.components.header.Header
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderUtils
import com.alejandro.proyecto_cines_frame.ui.logic.MovieUiMapper
import com.alejandro.proyecto_cines_frame.ui.logic.formatters.SessionRangeFormatter
import com.alejandro.proyecto_cines_frame.ui.logic.presenter.LoginPresenter
import com.alejandro.proyecto_cines_frame.ui.logic.presenter.RegisterPresenter
import com.alejandro.proyecto_cines_frame.ui.logic.state.BannersUiState
import com.alejandro.proyecto_cines_frame.ui.logic.state.MainSessionsUiState
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.banner
import proyecto_cines_frame.composeapp.generated.resources.calendar
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    sesionRepository: SesionRepository? = null,
    bannerRepository: SesionRepository? = null
) {
    val moviesRepository = sesionRepository ?: remember {
        SesionRepositoryImpl(
            api = KtorSesionApi(HttpClientFactory.create())
        )
    }

    val bannerRepository = remember {
        BanerRepositoryImpl(
            api = KtorBanerApi(HttpClientFactory.create())
        )
    }

    //Para poder cambia de pantalla
    var currentScreen by remember { mutableStateOf("main") }

    val scope = rememberCoroutineScope()

    val cuentaRepository = remember {
        CuentaRepositoryImpl(
            api = KtorCuentaApi(HttpClientFactory.create()),
            secureStore = CredentialStoreFactory.create()
        )
    }


    val loginPresenter = remember(cuentaRepository, scope) {
        LoginPresenter(
            cuentaRepo = cuentaRepository,
            scope = scope
        )
    }

    val registerPresenter = remember(cuentaRepository, scope) {
        RegisterPresenter(
            cuentaRepo = cuentaRepository,
            scope = scope
        )
    }


    val listState = rememberLazyListState()

    //SEARCH
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    val authState by SessionManager.state.collectAsState()
    var isRestoringSession by remember { mutableStateOf(true) }

    //FILTER
    val availableDays = remember { buildCarteleraDays() }

    var filterState by remember {
        mutableStateOf(
            FilterState(
                selectedDay = availableDays.firstOrNull()
            )
        )
    }
    val calendarDays = remember { buildCalendarDays() }

    var isApiBlocked by remember {
        mutableStateOf(false)
    }

    var sessionState by remember {
        mutableStateOf(MainSessionsUiState(isLoading = true))
    }

    var bannerState by remember {
        mutableStateOf(BannersUiState(isLoading = true))
    }

    val fetchRange = remember(calendarDays) {
        SessionRangeFormatter.buildRangeForCalendarDays(calendarDays)
    }

    LaunchedEffect(cuentaRepository) {
        when (val result = cuentaRepository.autoLoginIfPossible()) {
            is ApiResult.Success -> {
                val cuenta = result.data
                if (cuenta != null) {
                    SessionManager.setSession(cuenta, cuenta.token)
                } else {
                    SessionManager.clearSession()
                }
            }

            is ApiResult.Error -> {
                SessionManager.clearSession()
            }
        }
        isRestoringSession = false
    }

    LaunchedEffect(moviesRepository, fetchRange) {
        if (fetchRange == null) {
            sessionState = MainSessionsUiState(
                isLoading = false,
                errorMessage = "No hay dias disponibles para filtrar sesiones."
            )
            return@LaunchedEffect
        }

        sessionState = MainSessionsUiState(isLoading = true)

        when (val result = moviesRepository.getByRangoHorario(fetchRange.first, fetchRange.second)) {
            is ApiResult.Success -> {
                sessionState = MainSessionsUiState(
                    sessions = result.data,
                    isLoading = false,
                    errorMessage = null
                )
            }

            is ApiResult.Error -> {
                isApiBlocked = result.error is AppError.Network || result.error is AppError.Server || result.error is AppError.Unauthorized
                sessionState = MainSessionsUiState(
                    sessions = emptyList(),
                    isLoading = false,
                    errorMessage = toMainScreenErrorMessage(result.error)
                )
            }
        }
    }

    LaunchedEffect(bannerRepository) {

        bannerState = BannersUiState(isLoading = true)

        when (val result = bannerRepository.getBanersToday()) {
            is ApiResult.Success -> {
                bannerState = BannersUiState(
                    banners = result.data,
                    isLoading = false,
                    errorMessage = null
                )
            }
            is ApiResult.Error -> {
                isApiBlocked = result.error is AppError.Network || result.error is AppError.Server || result.error is AppError.Unauthorized
                bannerState = BannersUiState(
                    banners = emptyList(),
                    isLoading = false,
                    errorMessage = toMainScreenErrorMessage(result.error)
                )
            }
        }
    }

    //TODO: Esto se debe descomentar en la versión oficial/final para que el usuario vea una pantalla de información si no hay conexión con la API
    /*
    if (isApiBlocked) {
        OfflineBannerScreen(
            message = "Ups! Parece que el servidor no responde. Puede que estemos bajo mantenimiento o que no tengas conexión a internet"
        )
        return
    }*/


    val allSessions = sessionState.sessions
    val allBaners = bannerState.banners

    //LÓGICA (fuera de UI)
    val (carteleraMovies, carteleraSessions) = remember(filterState, allSessions) {
        MovieUiMapper.getFilteredCartelera(allSessions, filterState)
    }

    val (estrenoMovies, estrenoSessions) = remember(allSessions) {
        MovieUiMapper.getEstrenos(allSessions)
    }

    //HEADER
    var buscadorEnfocado by rememberSaveable { mutableStateOf(false) }

    val opacidadOscuro by animateFloatAsState(
        targetValue = if (buscadorEnfocado) HeaderUtils.OpacidadOscurecimiento else 0f,
        animationSpec = tween(HeaderUtils.DuracionTransicionOscurecimientoMs),
        label = "opacidadOscurecimiento"
    )


    if (currentScreen == "login") {
        LoginScreen(
            onLoginSuccess = {
                currentScreen = "main"
            },
            presenter = loginPresenter
        )
        return
    }

    if (currentScreen == "register") {
        RegisterScreen(
            onRegisterSuccess = {
                currentScreen = "login"
            },
            presenter = registerPresenter
        )
        return
    }

    if (isRestoringSession) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = TextWhite)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {

        //HEADER
        Header(
            searchQuery = searchQuery,

            onSearchChange = {
                searchQuery = it
            },

            onSearchSubmit = { query ->
                if (query.length >= 3) {
                    isSearching = true
                }
            },

            onFocusChange = {
                buscadorEnfocado = it
            },

            onEntradasClick = {},

            onLoginClick = {
                currentScreen = "login"
            },

            onRegisterClick = {
                currentScreen = "register"
            },

            onLogoutClick = {
                scope.launch {
                    cuentaRepository.logout(clearRememberedCredentials = true)
                    SessionManager.clearSession()
                }
            },

            onMyAccountClick = {
                //TODO: Lanzar vista de "MiCuenta" con los datos de la cuenta y permitir modificación
            },

            isSessionActive = authState.isAuthenticated
        )

        Box(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                if(bannerState.isLoading){
                    item {
                        Text(
                            text = "Cargando banners...",
                            color = TextWhite,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }


                if (allBaners.isNotEmpty()) {
                    item {
                        Banner(images = allBaners.map { it.url })
                    }
                }

                item {
                    Text(
                        text = "ENTRADAS",
                        color = TextWhite,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }

                item {
                    Filter(
                        state = filterState,
                        onStateChange = { filterState = it },
                        availableDays = availableDays,
                        calendarDays = calendarDays,
                        calendarPainter = painterResource(Res.drawable.calendar)
                    )
                }
                item {
                    // 🔹 CARTELERA FILTRADA
                    MovieSection(
                        title = "CARTELERA",
                        movies = carteleraMovies,
                        sessions = carteleraSessions
                    )
                }

                if (sessionState.isLoading) {
                    item {
                        Text(
                            text = "Cargando sesiones...",
                            color = TextWhite,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }

                item {
                    MovieSection(
                        title = "PRÓXIMOS ESTRENOS",
                        movies = estrenoMovies,
                        sessions = estrenoSessions
                    )
                }
                item {
                    Spacer(Modifier.height(24.dp))
                    Footer()
                }
            }
            //filtro oscuro
            if (buscadorEnfocado) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = opacidadOscuro))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            buscadorEnfocado = false
                        }
                )
            }
        }
    }
}

/**
 * Muestra mensajes de error en la pantalla principal si falla algo.
 */
private fun toMainScreenErrorMessage(error: AppError): String =
    when (error) {
        is AppError.Network -> error.message ?: "Error de red"
        is AppError.Unknown -> error.message ?: "Error desconocido"
        is AppError.Validation -> "Error de validacion"
        is AppError.Unauthorized -> "No autorizado"
        is AppError.Forbidden -> "Acceso denegado"
        is AppError.NotFound -> "No se encontraron sesiones"
        is AppError.Conflict -> "Conflicto en la peticion"
        is AppError.Server -> "Error del servidor (${error.code})"
    }

