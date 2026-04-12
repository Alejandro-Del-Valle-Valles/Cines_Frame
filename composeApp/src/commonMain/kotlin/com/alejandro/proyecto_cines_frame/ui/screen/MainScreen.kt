package com.alejandro.proyecto_cines_frame.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.ui.components.banner.Banner
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.MovieMockData
import com.alejandro.proyecto_cines_frame.ui.components.features.movies.MovieSection
import com.alejandro.proyecto_cines_frame.ui.components.filter.*
import com.alejandro.proyecto_cines_frame.ui.components.footer.Footer
import com.alejandro.proyecto_cines_frame.ui.components.header.Header
import com.alejandro.proyecto_cines_frame.ui.components.header.HeaderUtils
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.banner
import proyecto_cines_frame.composeapp.generated.resources.calendar

@Composable
fun MainScreen() {

    val allSesions = MovieMockData.getSesiones()

    val carteleraBase = allSesions.filter { it.pelicula.estado == PeliculaEstado.CARTELERA }
    val estrenosMovies = allSesions.filter { it.pelicula.estado == PeliculaEstado.ESTRENO }

    //Para poder cambia de pantalla
    var currentScreen by remember { mutableStateOf("main") }

    //SEARCH
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    //FILTER
    var filterState by remember { mutableStateOf(FilterState()) }

    val availableDays = remember { buildCarteleraDays() }
    val calendarDays = remember { buildCalendarDays() }

    /*FILTRO
    val filteredCartelera = remember<List<Pelicula>>(filterState, carteleraBase, searchQuery) {
        carteleraBase
            .applyFilters(filterState)
            .applySearch(searchQuery)
    }*/

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
            }
        )
        return
    }

    if (currentScreen == "register") {
        RegisterScreen(
            onRegisterSuccess = {
                currentScreen = "main"
            }
        )
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

            onEntradasClick = {

            },

            onLoginClick = {
                currentScreen = "login"
            },

            onRegisterClick = {
                currentScreen = "register"
            },

            onLogoutClick = {

            }
        )
        Box(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Banner(
                        images = listOf(
                            painterResource(Res.drawable.banner)
                        )
                    )
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
                    /*
                    if (filteredCartelera.isEmpty()) {
                        EmptyMoviesMessage()
                    } else {
                        MovieGrid(
                            movies = filteredCartelera,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }*/
                }
                item {
                    MovieSection(
                        title = "PRÓXIMOS ESTRENOS",
                        movies = estrenosMovies.map { it.pelicula },
                        sessions = listOf()
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

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreenMain() {
    MaterialTheme {
        MainScreen()
    }
}
