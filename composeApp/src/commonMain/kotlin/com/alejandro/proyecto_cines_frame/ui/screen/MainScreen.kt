package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.alejandro.proyecto_cines_frame.core.error.AppError
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.security.CredentialStoreFactory
import com.alejandro.proyecto_cines_frame.core.session.SessionManager
import com.alejandro.proyecto_cines_frame.data.remote.api.*
import com.alejandro.proyecto_cines_frame.data.remote.client.HttpClientFactory
import com.alejandro.proyecto_cines_frame.data.repository.*
import com.alejandro.proyecto_cines_frame.domain.enums.ParticipanteRol
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.model.Compra
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.domain.repository.*
import com.alejandro.proyecto_cines_frame.ui.components.banner.Banner
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.MovieSection
import com.alejandro.proyecto_cines_frame.ui.components.filter.*
import com.alejandro.proyecto_cines_frame.ui.components.header.Header
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderUtils
import com.alejandro.proyecto_cines_frame.ui.components.header.filter.applySearch
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
import proyecto_cines_frame.composeapp.generated.resources.calendar
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    sesionRepository: SesionRepository? = null,
    compraRepository: CompraRepository? = null,
    bannerRepository: BanerRepository? = null,
    salaRepository: SalaRepository? = null,
    peliculaRepository: PeliculaRepository? = null
) {
    val moviesRepository = sesionRepository ?: remember {
        SesionRepositoryImpl(
            api = KtorSesionApi(HttpClientFactory.create())
        )
    }
    
    val comprasRepository = compraRepository ?: remember {
        CompraRepositoryImpl(
            api = KtorCompraApi(HttpClientFactory.create())
        )
    }

    val banerRepository = bannerRepository ?: remember {
        BanerRepositoryImpl(
            api = KtorBanerApi(HttpClientFactory.create())
        )
    }

    val salasRepository = salaRepository ?: remember {
        SalaRepositoryImpl(
            api = KtorSalaApi(HttpClientFactory.create())
        )
    }

    val pelisRepository = peliculaRepository ?: remember {
        PeliculaRepositoryImpl(
            api = KtorPeliculaApi(HttpClientFactory.create())
        )
    }

    //Para poder cambia de pantalla
    var currentScreen by remember { mutableStateOf("main") }

    val snackbarHostState = remember { SnackbarHostState() }

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

    var checkoutSession by remember { mutableStateOf<Sesion?>(null) }
    var checkoutSalaCapacity by remember { mutableStateOf(0) }
    var isOpeningCheckout by remember { mutableStateOf(false) }
    var checkoutErrorMessage by remember { mutableStateOf<String?>(null) }

    var profileCompras by remember { mutableStateOf<List<Compra>>(emptyList()) }
    var profileMovieTitles by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var isLoadingProfile by remember { mutableStateOf(false) }
    var profileErrorMessage by remember { mutableStateOf<String?>(null) }

    // Detalle Película
    var selectedMovieDetail by remember { mutableStateOf<Pelicula?>(null) }
    var isLoadingMovieDetail by remember { mutableStateOf(false) }

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
                isApiBlocked =
                    result.error is AppError.Network ||
                            result.error is AppError.Server ||
                            result.error is AppError.Unauthorized

                sessionState = MainSessionsUiState(
                    sessions = emptyList(),
                    isLoading = false,
                    errorMessage = toMainScreenErrorMessage(result.error)
                )
            }
        }
    }

    LaunchedEffect(banerRepository) {
        bannerState = BannersUiState(isLoading = true)

        when (val result = banerRepository.getBanersToday()) {
            is ApiResult.Success -> {
                bannerState = BannersUiState(
                    banners = result.data,
                    isLoading = false,
                    errorMessage = null
                )
            }

            is ApiResult.Error -> {
                isApiBlocked =
                    result.error is AppError.Network ||
                            result.error is AppError.Server ||
                            result.error is AppError.Unauthorized

                bannerState = BannersUiState(
                    banners = emptyList(),
                    isLoading = false,
                    errorMessage = toMainScreenErrorMessage(result.error)
                )
            }
        }
    }

    LaunchedEffect(currentScreen, authState.isAuthenticated) {
        if (currentScreen != "profile") return@LaunchedEffect

        if (!authState.isAuthenticated) {
            profileErrorMessage = "Debes iniciar sesion para ver tus entradas."
            return@LaunchedEffect
        }

        isLoadingProfile = true
        profileErrorMessage = null

        when (val comprasResult = comprasRepository.getAll()) {
            is ApiResult.Success -> profileCompras = comprasResult.data
            is ApiResult.Error -> {
                profileCompras = emptyList()
                profileErrorMessage = toMainScreenErrorMessage(comprasResult.error)
            }
        }

        when (val peliculasResult = pelisRepository.getAllBasic()) {
            is ApiResult.Success -> {
                profileMovieTitles = peliculasResult.data.associate { it.id to it.nombre }
            }
            is ApiResult.Error -> {
                profileMovieTitles = emptyMap()
                if (profileErrorMessage == null) {
                    profileErrorMessage = toMainScreenErrorMessage(peliculasResult.error)
                }
            }
        }

        isLoadingProfile = false
    }

    val allSessions = sessionState.sessions
    val allBaners = bannerState.banners

    //LÓGICA (fuera de UI)
    val (carteleraMoviesBase, carteleraSessions) = remember(filterState, allSessions) {
        MovieUiMapper.getFilteredCartelera(allSessions, filterState)
    }

    val carteleraMovies = remember(carteleraMoviesBase, searchQuery) {
        carteleraMoviesBase.applySearch(searchQuery)
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

    LaunchedEffect(searchQuery) {
        if (searchQuery.trim().length >= 3) {
            listState.animateScrollToItem(3)
        }
    }

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

    if (currentScreen == "checkout" && checkoutSession != null) {
        CheckoutScreen(
            session = checkoutSession!!,
            salaCapacity = checkoutSalaCapacity,
            onBack = {
                currentScreen = "main"
            },
            sesionRepository = moviesRepository
        )
        return
    }

    val openCheckoutForSession: (Sesion) -> Unit = { sessionClicked ->
        scope.launch {
            isOpeningCheckout = true
            checkoutErrorMessage = null

            val sesionResult = moviesRepository.getSesion(
                numSala = sessionClicked.numSala,
                peliculaId = sessionClicked.pelicula.id,
                horario = sessionClicked.horario.toString()
            )

            val sesionDetalle = when (sesionResult) {
                is ApiResult.Success -> sesionResult.data
                is ApiResult.Error -> {
                    checkoutErrorMessage = toMainScreenErrorMessage(sesionResult.error)
                    isOpeningCheckout = false
                    return@launch
                }
            }

            val salaResult = salasRepository.getByNumero(sesionDetalle.numSala)

            when (salaResult) {
                is ApiResult.Success -> {
                    checkoutSession = sesionDetalle
                    checkoutSalaCapacity = salaResult.data.aforo
                    currentScreen = "checkout"
                }

                is ApiResult.Error -> {
                    checkoutErrorMessage = toMainScreenErrorMessage(salaResult.error)
                }
            }

            isOpeningCheckout = false
        }
    }

    if (currentScreen == "movie_detail" && selectedMovieDetail != null) {
        MovieDetailScreen(
            title = selectedMovieDetail!!.nombre,
            description = selectedMovieDetail!!.descripcion,
            directors = selectedMovieDetail!!.creditos
                .filter { it.roles.contains(ParticipanteRol.DIRECTOR) }
                .joinToString { it.participante.nombre },
            actors = selectedMovieDetail!!.creditos
                .filter { it.roles.contains(ParticipanteRol.ACTOR) }
                .joinToString { it.participante.nombre },
            duration = selectedMovieDetail!!.duracion.toString().substring(0, 5), // HH:mm
            ageRating = "+${selectedMovieDetail!!.calificacionEdad ?: 0}",
            imagePainter = rememberAsyncImagePainter(selectedMovieDetail!!.portada),
            sessions = selectedMovieDetail!!.sesiones,
            onSessionClick = openCheckoutForSession,
            onBackClick = {
                currentScreen = "main"
                selectedMovieDetail = null
            }
        )
        return
    }

    if (currentScreen == "profile") {
        UserProfileScreen(
            userName = authState.cuenta?.nombre ?: "",
            compras = profileCompras,
            movieTitlesById = profileMovieTitles,
            errorMessage = profileErrorMessage,
            onChangeName = {},
            onChangePassword = {},
            onNameChanged = {},
            onBackClick = {
                currentScreen = "main"
            }
        )

        if (isLoadingProfile) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = TextWhite)
            }
        }
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

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundDark)
        ) {

            //HEADER
            Header(
                searchQuery = searchQuery,

                onSearchChange = {
                    searchQuery = it
                },

                onSearchSubmit = {
                    scope.launch {
                        listState.animateScrollToItem(3)
                    }
                },

                onFocusChange = {
                    buscadorEnfocado = it
                },

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
                        snackbarHostState.showSnackbar(
                            message = "Se ha cerrado sesión correctamente",
                            duration = SnackbarDuration.Short
                        )
                    }
                },

                onMyAccountClick = {
                    if (authState.isAuthenticated) {
                        currentScreen = "profile"
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Debes iniciar sesion para ver tu cuenta",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },

                isSessionActive = authState.isAuthenticated
            )

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    if (bannerState.isLoading) {
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
                            sessions = carteleraSessions,
                            onMovieClick = { movieClicked ->
                                scope.launch {
                                    isLoadingMovieDetail = true
                                    when (val result = pelisRepository.getByIdWithSesiones(movieClicked.id)) {
                                        is ApiResult.Success -> {
                                            selectedMovieDetail = result.data
                                            currentScreen = "movie_detail"
                                        }

                                        is ApiResult.Error -> {
                                            snackbarHostState.showSnackbar(
                                                message = "Ha ocurrido un error al tratar de filtrar películas",
                                                duration = SnackbarDuration.Long
                                            )
                                        }
                                    }
                                    isLoadingMovieDetail = false
                                }
                            },
                            onSessionClick = { sessionClicked ->
                                openCheckoutForSession(sessionClicked)
                            }
                        )
                    }

                    // 🔹 PRÓXIMOS ESTRENOS
                    if (estrenoMovies.isNotEmpty()) {
                        item {
                            MovieSection(
                                title = "PRÓXIMOS ESTRENOS",
                                movies = estrenoMovies,
                                sessions = estrenoSessions,
                                onMovieClick = { movieClicked ->
                                    scope.launch {
                                        isLoadingMovieDetail = true
                                        when (val result = pelisRepository.getById(movieClicked.id)) {
                                            is ApiResult.Success -> {
                                                selectedMovieDetail = result.data
                                                currentScreen = "movie_detail"
                                            }

                                            is ApiResult.Error -> {
                                                snackbarHostState.showSnackbar(
                                                    message = "Ha ocurrido un error al tratar de obtener los próximos estrenos",
                                                    duration = SnackbarDuration.Long
                                                )
                                            }
                                        }
                                        isLoadingMovieDetail = false
                                    }
                                }
                            )
                        }
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

                    if (sessionState.errorMessage != null) {
                        item {
                            Text(
                                text = sessionState.errorMessage!!,
                                color = Color.Red,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                    }
                }

                if (isLoadingMovieDetail || isOpeningCheckout) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = TextWhite)
                    }
                }
            }
        }
    }
}

private fun toMainScreenErrorMessage(error: AppError): String {
    return when (error) {
        is AppError.Network -> "Error de red. Comprueba tu conexión."
        is AppError.Server -> "Error del servidor. Inténtalo más tarde."
        is AppError.Unauthorized -> "No autorizado."
        else -> "Ha ocurrido un error inesperado."
    }
}
