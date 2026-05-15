package com.alejandro.proyecto_cines_frame.ui.components.admin.MovieManagementScreens.AddEditMovie

import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.api.KtorParticipanteApi
import com.alejandro.proyecto_cines_frame.data.remote.client.HttpClientFactory
import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCreateDTO
import com.alejandro.proyecto_cines_frame.data.repository.ParticipanteRepositoryImpl
import com.alejandro.proyecto_cines_frame.domain.enums.ParticipanteRol
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.model.Participante
import com.alejandro.proyecto_cines_frame.domain.extension.toFirstUiMessagePerField
import com.alejandro.proyecto_cines_frame.domain.model.input.PeliculaCreateInput
import com.alejandro.proyecto_cines_frame.domain.validation.PeliculaValidator
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMovieMovile(
    movie: Pelicula?,
    onSave: (PeliculaCreateDTO) -> Unit
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
            movie?.duracion?.hour?.toString()?.padStart(2, '0') ?: ""
        )
    }

    var duracionMinutos by remember {
        mutableStateOf(
            movie?.duracion?.minute?.toString()?.padStart(2, '0') ?: ""
        )
    }

    var clasificacionEdad by remember {
        mutableStateOf(
            movie?.calificacionEdad?.toString() ?: ""
        )
    }

    var portada by remember {
        mutableStateOf(movie?.portada ?: "")
    }

    var estado by remember {
        mutableStateOf(movie?.estado ?: PeliculaEstado.INACTIVA)
    }

    var genero by remember {
        mutableStateOf(movie?.genero ?: PeliculaGenero.COMEDIA)
    }

    var fieldErrors by remember {
        mutableStateOf<Map<String, String>>(emptyMap())
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
        var id: Int,
        var nombre: String,
        var rol: ParticipanteRol
    )

    val participantes = remember {
        mutableStateListOf<ParticipanteUi>().apply {
            movie?.creditos?.forEach { credito ->
                val roles = credito.roles
                if (roles.isEmpty()) {
                    add(
                        ParticipanteUi(
                            id = credito.participante.id,
                            nombre = credito.participante.nombre,
                            rol = ParticipanteRol.ACTOR
                        )
                    )
                } else {
                    roles.forEach { rol ->
                        add(
                            ParticipanteUi(
                                id = credito.participante.id,
                                nombre = credito.participante.nombre,
                                rol = rol
                            )
                        )
                    }
                }
            }
        }
    }

    val participanteRepository = remember {
        ParticipanteRepositoryImpl(
            api = KtorParticipanteApi(HttpClientFactory.create())
        )
    }

    var participantesDisponibles by remember {
        mutableStateOf<List<Participante>>(emptyList())
    }

    var participantesError by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(participanteRepository) {
        participantesError = null
        when (val result = participanteRepository.getAll()) {
            is ApiResult.Success -> participantesDisponibles = result.data
            is ApiResult.Error -> participantesError = "Error al cargar participantes"
        }
    }

    var selectedParticipant by remember {
        mutableStateOf<Participante?>(null)
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
        // PORTADA (URL)
        // =====================================================

        Text(
            text = "Portada (URL):",
            color = TextWhite
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = portada,
            onValueChange = {
                portada = it
            },
            modifier = Modifier.fillMaxWidth()
        )

        fieldErrors["url"]?.let {
            Spacer(modifier = Modifier.height(6.dp))
            Text(it, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // =====================================================
        // DURACIÓN / CLASIFICACIÓN / ESTADO
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
                        onValueChange = { nuevoValor ->
                            if (nuevoValor.all { it.isDigit() } && nuevoValor.length <= 2) {
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
                        onValueChange = { nuevoValor ->
                            if (nuevoValor.all { it.isDigit() } && nuevoValor.length <= 2) {
                                duracionMinutos = nuevoValor
                            }
                        },
                        modifier = Modifier.width(70.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }

                fieldErrors["duracion"]?.let {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(it, color = Color.Red)
                }
            }

            // 🔞 EDAD
            Column {

                FormLabel("Clasificación de edad:")

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = clasificacionEdad,
                    onValueChange = { nuevoValor ->
                        if (nuevoValor.all { it.isDigit() }) {
                            clasificacionEdad = nuevoValor
                        }
                    },
                    modifier = Modifier.width(180.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                fieldErrors["edad"]?.let {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(it, color = Color.Red)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // =====================================================
        // ESTADO
        // =====================================================

        Text(
            text = "Estado:",
            color = TextWhite
        )

        Spacer(modifier = Modifier.height(8.dp))

        var expandedEstado by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expandedEstado,
            onExpandedChange = { expandedEstado = !expandedEstado }
        ) {
            OutlinedTextField(
                value = estado.name,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEstado)
                }
            )

            ExposedDropdownMenu(
                expanded = expandedEstado,
                onDismissRequest = { expandedEstado = false }
            ) {
                PeliculaEstado.entries.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion.name) },
                        onClick = {
                            estado = opcion
                            expandedEstado = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // =====================================================
        // GÉNERO
        // =====================================================

        Text(
            text = "Género:",
            color = TextWhite
        )

        Spacer(modifier = Modifier.height(12.dp))

        var expandedGenero by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expandedGenero,
            onExpandedChange = { expandedGenero = !expandedGenero }
        ) {
            OutlinedTextField(
                value = genero.label,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenero)
                }
            )

            ExposedDropdownMenu(
                expanded = expandedGenero,
                onDismissRequest = { expandedGenero = false }
            ) {
                PeliculaGenero.entries.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion.label) },
                        onClick = {
                            genero = opcion
                            expandedGenero = false
                        }
                    )
                }
            }
        }

        fieldErrors["genero"]?.let {
            Spacer(modifier = Modifier.height(6.dp))
            Text(it, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val duracionValue = "${duracionHoras.trim()}:${duracionMinutos.trim()}"
                val enCarteleraValue = when (estado) {
                    PeliculaEstado.CARTELERA -> true
                    PeliculaEstado.ESTRENO -> false
                    PeliculaEstado.INACTIVA -> null
                }

                val input = PeliculaCreateInput(
                    nombre = titulo,
                    descripcion = descripcion.takeIf { it.isNotBlank() },
                    genero = genero,
                    url = portada.takeIf { it.isNotBlank() },
                    duracion = duracionValue,
                    edad = clasificacionEdad.toIntOrNull(),
                    enCartelera = enCarteleraValue,
                    participantes = emptyList()
                )

                val errors = PeliculaValidator.validateCreate(input).toFirstUiMessagePerField()
                if (errors.isNotEmpty()) {
                    fieldErrors = errors
                    return@Button
                }

                fieldErrors = emptyMap()

                onSave(
                    PeliculaCreateDTO(
                        nombre = input.nombre.trim(),
                        descripcion = input.descripcion?.trim(),
                        genero = genero,
                        url = input.url?.trim(),
                        duracion = input.duracion.trim(),
                        edad = input.edad,
                        enCartelera = input.enCartelera,
                        participantes = participantes.map { participante ->
                            ParticipanteCreateDTO(
                                id = participante.id,
                                roles = listOf(participante.rol)
                            )
                        }
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = OtroRojo),
            shape = RectangleShape
        ) {
            Text("Guardar", color = TextWhite)
        }

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

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        participantes.removeAt(index)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OtroRojo
                    ),
                    shape = RectangleShape
                ) {
                    Text("Eliminar", color = TextWhite)
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

        val filteredNames = participantesDisponibles.filter {
            it.nombre.contains(participantSearch, ignoreCase = true)
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

                        filteredNames.take(5).forEach { participante ->

                            Text(
                                text = participante.nombre,
                                color = TextWhite,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        participantSearch = participante.nombre
                                        selectedParticipant = participante
                                    }
                                    .padding(8.dp)
                            )
                        }

                        if (participantesError != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(participantesError ?: "", color = Color.Red)
                        }
                    }
                }
            },

            confirmButton = {

                Button(
                    onClick = {

                        val match = selectedParticipant
                            ?: participantesDisponibles.firstOrNull {
                                it.nombre.equals(participantSearch.trim(), ignoreCase = true)
                            }

                        if (match != null) {
                            participantes.add(
                                ParticipanteUi(
                                    id = match.id,
                                    nombre = match.nombre,
                                    rol = ParticipanteRol.ACTOR
                                )
                            )

                            participantSearch = ""
                            selectedParticipant = null
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
                        selectedParticipant = null
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