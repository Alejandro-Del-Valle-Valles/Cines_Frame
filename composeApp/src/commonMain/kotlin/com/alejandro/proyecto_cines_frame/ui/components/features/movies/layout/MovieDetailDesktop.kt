package com.alejandro.proyecto_cines_frame.ui.components.features.movies.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton
import com.alejandro.proyecto_cines_frame.ui.screen.MovieDetailScreen
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo
import org.jetbrains.compose.resources.painterResource
import proyecto_cines_frame.composeapp.generated.resources.Res
import proyecto_cines_frame.composeapp.generated.resources.banner


@Composable
fun MovieDetailDesktop(
    title: String,
    description: String,
    directors: String,
    actors: String,
    duration: String,
    ageRating: String,
    imagePainter: Painter,
    onBackClick: () -> Unit
) {

    val background = Color(0xFF121212)
    val cardBackground = Color(0xFF1A1A1A)
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

                ScheduleRow("2D", listOf("20:30", "22:15"))
                ScheduleRow("3D", listOf("18:00", "21:00"))
                ScheduleRow("VOSE", listOf("17:30", "19:45"))
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
fun ScheduleRow(format: String, times: List<String>) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = format,
                color = Color.White,
                modifier = Modifier.width(40.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                times.forEach {
                    TimeChip(it)
                }
            }
        }
    }
}

@Composable
fun TimeChip(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFFD32F2F), RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, color = Color.White)
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
            imagePainter = painterResource(Res.drawable.banner), // Aquí va la imagen de prueba
            onBackClick = {}
        )
    }
}