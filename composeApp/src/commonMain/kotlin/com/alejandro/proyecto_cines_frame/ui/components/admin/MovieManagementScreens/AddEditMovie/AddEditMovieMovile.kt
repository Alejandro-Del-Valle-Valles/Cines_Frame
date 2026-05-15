package com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens.AddEditMovie

import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.proyecto_cines_frame.domain.enums.ParticipanteRol
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMovieMovile(
    movie: Pelicula?, // 🔥 null = añadir | con datos = modificar
    onSave: (Pelicula) -> Unit,
    onDeleteGenre: (PeliculaGenero) -> Unit,
    onSelectImage: () -> Unit
) {

    // =========================================================
    // SCROLL
    // =========================================================

    val background = BackgroundDark
    val panelColor = ColorFondoHeader
    val scrollState = rememberScrollState()

    // =========================================================
    // ESTADOS
    // =========================================================

    var titulo by remember {
        mutableStateOf(movie?.nombre ?: "")
    }

    var descripcion by remember {
        mutableStateOf(movie?.descripcion ?: "")
    }

    var duracionHoras by remember {
        mutableStateOf(
            movie?.duracion?.hour?.toString() ?: ""
        )
    }

    var duracionMinutos by remember {
        mutableStateOf(
            movie?.duracion?.minute?.toString() ?: ""
        )
    }

    var clasificacionEdad by remember {
        mutableStateOf(
            movie?.calificacionEdad?.toString() ?: ""
        )
    }

    var enCartelera by remember {
        mutableStateOf(
            movie?.estado == PeliculaEstado.CARTELERA
        )
    }

    // =========================================================
    // GÉNEROS
    // =========================================================

    data class GeneroUi(
        var genero: PeliculaGenero
    )

    var generos by remember {

        mutableStateOf(

            mutableListOf(
                GeneroUi(
                    genero = movie?.genero ?: PeliculaGenero.COMEDIA
                )
            )
        )
    }

    // =========================================================
    // PARTICIPANTES
    // =========================================================

    data class ParticipanteUi(
        var nombre: String,
        var rol: ParticipanteRol
    )

    var participantes by remember {

        mutableStateOf(
            mutableListOf(
                ParticipanteUi(
                    nombre = "Manolo Apellidini",
                    rol = ParticipanteRol.DIRECTOR
                ),
                ParticipanteUi(
                    nombre = "Manolini Apellidito",
                    rol = ParticipanteRol.ACTOR
                )
            )
        )
    }

    // =========================================================
    // POPUP PARTICIPANTES
    // =========================================================

    var showAddParticipantDialog by remember {
        mutableStateOf(false)
    }

    var participantSearch by remember {
        mutableStateOf("")
    }

    val mockNames = remember {

        mutableStateListOf(
            "Robert Downey Jr",
            "Scarlett Johansson",
            "Leonardo DiCaprio",
            "Brad Pitt",
            "Christopher Nolan",
            "Anne Hathaway"
        )
    }

    // =========================================================
    // UI
    // =========================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        // =====================================================
        // TÍTULO
        // =====================================================

        Text(
            text = if (movie == null)
                "Añadir película"
            else
                "Modificar película",

            color = TextWhite,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.height(24.dp))

        // =====================================================
        // TÍTULO INPUT
        // =====================================================

        Text(
            text = "Título:",
            color = TextWhite
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = {
                titulo = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // =====================================================
        // DESCRIPCIÓN
        // =====================================================

        Text(
            text = "Descripción:",
            color = TextWhite
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // =====================================================
        // PORTADA
        // =====================================================

        Text(
            text = "Portada:",
            color = TextWhite
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = OtroRojo
                ),
                shape = RectangleShape
            ) {

                Text(
                    "Examinar",
                    color = TextWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // =====================================================
        // DURACIÓN / CLASIFICACIÓN / CARTELERA
        // =====================================================

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ⏱ DURACIÓN
            Column {

                FormLabel("Duración:")

                Spacer(modifier = Modifier.height(8.dp))

                Row {

                    TextField(
                        value = duracionHoras,
                        onValueChange = {
                                nuevoValor ->
                            // Sólo números y :
                            if (nuevoValor.all { it.isDigit() || it == ':' }) {
                                duracionHoras = nuevoValor
                            }
                        },
                        modifier = Modifier.width(70.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )

                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        ":",
                        color = TextWhite,
                        fontSize = 30.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextField(
                        value = duracionMinutos,
                        onValueChange = {
                                nuevoValor ->
                            // Sólo números y :
                            if (nuevoValor.all { it.isDigit() || it == ':' }) {
                                duracionMinutos = nuevoValor
                            }
                        },
                        modifier = Modifier.width(70.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }

            // 🔞 EDAD
            Column {

                FormLabel("Clasificación de edad:")

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = clasificacionEdad,
                    onValueChange = {
                            nuevoValor ->
                        // Sólo números
                        if (nuevoValor.all { it.isDigit() }) {
                            clasificacionEdad = nuevoValor
                        }
                    },
                    modifier = Modifier.width(180.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }

            // 🎬 CARTELERA
            //TODO: Esto no he podido verlo en móvil porque no me ejecuta,
            // pero si no se ve bien sólo hay que sacar esto de la row para que se ponga debajo:

            Column {

                FormLabel("En cartelera:")

                Spacer(modifier = Modifier.height(8.dp))

                Checkbox(
                    checked = enCartelera,
                    onCheckedChange = {
                        enCartelera = it
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =====================================================
        // GÉNEROS
        // =====================================================

        Text(
            text = "Géneros:",
            color = TextWhite
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            generos.forEachIndexed { index, generoUi ->

                var expandedGenero by remember {
                    mutableStateOf(false)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    ExposedDropdownMenuBox(
                        expanded = expandedGenero,
                        onExpandedChange = {
                            expandedGenero = !expandedGenero
                        }
                    ) {

                        OutlinedTextField(
                            value = generoUi.genero.label,
                            onValueChange = {},
                            readOnly = true,

                            modifier = Modifier
                                .menuAnchor()
                                .weight(1f),

                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedGenero
                                )
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = expandedGenero,
                            onDismissRequest = {
                                expandedGenero = false
                            }
                        ) {

                            PeliculaGenero.entries.forEach { genero ->

                                DropdownMenuItem(
                                    text = {
                                        Text(genero.label)
                                    },

                                    onClick = {

                                        generos[index] =
                                            generoUi.copy(
                                                genero = genero
                                            )

                                        expandedGenero = false
                                    }
                                )
                            }
                        }
                    }

                    // BOTÓN ELIMINAR

                    Button(
                        onClick = {

                            if (generos.size > 1) {
                                generos.removeAt(index)
                            }
                        },

                        colors = ButtonDefaults.buttonColors(
                            containerColor = OtroRojo
                        ),

                        shape = RectangleShape,

                        modifier = Modifier.size(40.dp),

                        contentPadding = PaddingValues(0.dp)
                    ) {

                        Text(
                            "🗑",
                            color = TextWhite
                        )
                    }
                }
            }

            // BOTÓN AÑADIR GÉNERO

            Button(
                onClick = {

                    generos.add(
                        GeneroUi(
                            genero = PeliculaGenero.COMEDIA
                        )
                    )
                },

                colors = ButtonDefaults.buttonColors(
                    containerColor = panelColor
                ),

                shape = RectangleShape,

                modifier = Modifier.size(35.dp),

                contentPadding = PaddingValues(0.dp)
            ) {

                Text(
                    "+",
                    color = TextWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // =====================================================
        // PARTICIPANTES
        // =====================================================

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Participantes:",
                color = TextWhite,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = {
                    showAddParticipantDialog = true
                },

                colors = ButtonDefaults.buttonColors(
                    containerColor = OtroRojo
                ),

                shape = RectangleShape
            ) {

                Text(
                    "Añadir participante",
                    color = TextWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        participantes.forEachIndexed { index, participante ->

            var expandedRol by remember {
                mutableStateOf(false)
            }

            Column {

                Text(
                    text = "Nombre:",
                    color = TextWhite
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = participante.nombre,
                    color = TextWhite
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Rol:",
                    color = TextWhite
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedRol,
                    onExpandedChange = {
                        expandedRol = !expandedRol
                    }
                ) {

                    OutlinedTextField(
                        value = participante.rol.name,
                        onValueChange = {},
                        readOnly = true,

                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),

                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expandedRol
                            )
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expandedRol,
                        onDismissRequest = {
                            expandedRol = false
                        }
                    ) {

                        ParticipanteRol.entries.forEach { rol ->

                            DropdownMenuItem(
                                text = {
                                    Text(rol.name)
                                },

                                onClick = {

                                    participantes[index] =
                                        participante.copy(
                                            rol = rol
                                        )

                                    expandedRol = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(
                    color = TextWhite.copy(alpha = 0.4f)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // =========================================================
    // POPUP AÑADIR PARTICIPANTE
    // =========================================================

    if (showAddParticipantDialog) {

        val filteredNames = mockNames.filter {
            it.contains(participantSearch, ignoreCase = true)
        }

        AlertDialog(
            onDismissRequest = {
                showAddParticipantDialog = false
            },

            containerColor = panelColor,

            title = {
                Text(
                    "Añadir participante",
                    color = TextWhite
                )
            },

            text = {

                Column {

                    OutlinedTextField(
                        value = participantSearch,
                        onValueChange = {
                            participantSearch = it
                        },

                        label = {
                            Text("Nombre")
                        },

                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {

                        filteredNames.take(5).forEach { name ->

                            Text(
                                text = name,
                                color = TextWhite,

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        participantSearch = name
                                    }
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            },

            confirmButton = {

                Button(
                    onClick = {

                        if (participantSearch.isNotBlank()) {

                            if (!mockNames.contains(participantSearch)) {
                                mockNames.add(participantSearch)
                            }

                            participantes.add(
                                ParticipanteUi(
                                    nombre = participantSearch,
                                    rol = ParticipanteRol.ACTOR
                                )
                            )

                            participantSearch = ""
                            showAddParticipantDialog = false
                        }
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = OtroRojo
                    ),

                    shape = RectangleShape
                ) {

                    Text(
                        "Añadir",
                        color = TextWhite
                    )
                }
            },

            dismissButton = {

                Button(
                    onClick = {

                        showAddParticipantDialog = false
                        participantSearch = ""
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = panelColor
                    ),

                    shape = RectangleShape
                ) {

                    Text(
                        "Cancelar",
                        color = TextWhite
                    )
                }
            }
        )
    }
}