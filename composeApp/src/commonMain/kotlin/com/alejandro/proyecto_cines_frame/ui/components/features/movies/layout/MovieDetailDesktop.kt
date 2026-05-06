package com.alejandro.proyecto_cines_frame.ui.components.features.movies.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.components.common.ToggleText
import com.alejandro.proyecto_cines_frame.ui.components.session.formatDate
import com.alejandro.proyecto_cines_frame.ui.components.session.formatTime
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.logo_frames


@Composable
fun MovieDetailDesktop(
    title: String,
    description: String,
    directors: String,
    actors: String,
    duration: String,
    ageRating: String,
    imagePainter: Painter,
    sessions: List<Sesion>,
    onSessionClick: (Sesion) -> Unit,
    onBackClick: () -> Unit
) {

    val background = Color(0xFF121212)
    val cardBackground = Color(0xFF1A1A1A)
    var is3D by remember { mutableStateOf<Boolean?>(null) }
    var isVOSE by remember { mutableStateOf(false) }

    val filteredSessions = remember(sessions, is3D, isVOSE) {
        sessions
            .filter { session ->
                val match3D = is3D?.let { session.tresD == it } ?: true
                val matchVOSE = if (isVOSE) session.vose else true
                match3D && matchVOSE
            }
            .sortedBy { it.horario }
    }

    val sessionsByDate = remember(filteredSessions) {
        filteredSessions.groupBy { it.horario.date }.toSortedMap()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {

        // 🔙 BOTÓN (FORZADO ARRIBA IZQUIERDA)
        BackButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .zIndex(10f) // 🔥 clave por si algo lo tapa
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            // 🎬 POSTER
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .width(260.dp)
                    .height(380.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            // 🧾 CONTENIDO CENTRAL
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = title.uppercase(),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge
                )

                InfoSection("DIRECTOR", directors)
                InfoSection("ACTORES", actors)
                InfoSection("SINOPSIS", description)

                Spacer(modifier = Modifier.height(16.dp))

                // 🎟 HORARIOS
                Text(
                    text = "HORARIOS",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ToggleText("2D", is3D == false) {
                        is3D = if (is3D == false) null else false
                    }

                    Text("/", color = TextGray)

                    ToggleText("3D", is3D == true) {
                        is3D = if (is3D == true) null else true
                    }

                    ToggleText("VOSE", isVOSE) {
                        isVOSE = !isVOSE
                    }
                }

                if (sessionsByDate.isEmpty()) {
                    Text(
                        text = "No hay sesiones disponibles.",
                        color = Color.LightGray
                    )
                } else {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        sessionsByDate.forEach { (_, daySessions) ->
                            val label = formatDate(daySessions.first().horario)

                            DaySessionsBlock(
                                label = label,
                                sessions = daySessions.sortedBy { it.horario },
                                onSessionClick = onSessionClick
                            )
                        }
                    }
                }
            }

            // 📊 PANEL DERECHO
            Column(
                modifier = Modifier
                    .width(200.dp)
                    .background(cardBackground, RoundedCornerShape(16.dp))
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SideInfo("DURACIÓN", duration)
                Divider(color = Color.DarkGray)
                SideInfo("CLASIFICACIÓN", ageRating)
            }
        }
    }
}

@Composable
fun InfoSection(title: String, content: String) {
    Column {
        Text(title, color = Color.Gray)
        Text(content, color = Color.White)
    }
}

@Composable
private fun DaySessionsBlock(
    label: String,
    sessions: List<Sesion>,
    onSessionClick: (Sesion) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.titleSmall
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            sessions.forEach { session ->
                TimeChipRed(
                    text = formatTime(session),
                    onClick = { onSessionClick(session) }
                )
            }
        }
    }
}

@Composable
private fun TimeChipRed(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(OtroRojo, RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun SideInfo(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Color.Gray)
        Text(value, color = Color.White)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun PreviewMovieDetailScreen() {
    MaterialTheme {
        MovieDetailDesktop(
            title = "Interstellar",
            description = "Un grupo de exploradores viaja a través de un agujero de gusano en el espacio en un intento por asegurar la supervivencia de la humanidad.",
            directors = "Christopher Nolan",
            actors = "Matthew McConaughey, Anne Hathaway",
            duration = "169 min",
            ageRating = "+12",
            imagePainter = painterResource(Res.drawable.logo_frames), // Aquí va la imagen de prueba
            sessions = emptyList(),
            onSessionClick = {},
            onBackClick = {}
        )
    }
}