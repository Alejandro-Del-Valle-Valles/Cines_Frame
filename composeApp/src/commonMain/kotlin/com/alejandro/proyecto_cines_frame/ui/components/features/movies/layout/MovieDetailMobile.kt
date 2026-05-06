package com.alejandro.proyecto_cines_frame.ui.components.features.movies.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.components.common.ToggleText
import com.alejandro.proyecto_cines_frame.ui.components.session.formatDate
import com.alejandro.proyecto_cines_frame.ui.components.session.formatTime
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import com.alejandro.proyecto_cines_frame.ui.theme.TextGray
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun MovieDetailMovile(
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

    val scrollState = rememberScrollState()
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
            .background(Color(0xFF121212))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .verticalScroll(scrollState)
        ) {

            // 🎬 HERO IMAGE
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {

                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // 🔥 Overlay oscuro (para legibilidad)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                )

                // 🎬 Título
                Text(
                    text = title.uppercase(),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 📄 CONTENIDO
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                InfoBlock("DURACIÓN", duration)
                InfoBlock("CLASIFICACIÓN", ageRating)

                Text(
                    text = "No recomendada para menores de 7 años",
                    color = Color.LightGray
                )

                InfoBlock("DIRECTORES", directors)
                InfoBlock("ACTORES", actors)
                InfoBlock("SINOPSIS", description)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "HORARIOS",
                color = Color.Gray,
                style = MaterialTheme.typography.titleMedium
            )

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
        // 🔙 Botón back
        BackButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
    }
}

@Composable
fun InfoBlock(title: String, content: String) {
    Column {
        Text(
            text = title,
            color = Color.Gray
        )
        Text(
            text = content,
            color = Color.White
        )
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
