package com.alejandro.proyecto_cines_frame.ui.components.features.movies.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {

        // 🎬 COLUMNA IZQUIERDA (POSTER + BOTONES)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier
                    .width(220.dp)
                    .height(320.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* TODO comprar */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = OtroRojo,
                    contentColor = Color.White
                ),
                modifier = Modifier.width(220.dp)
            ) {
                Text("Comprar entradas")
            }
        }

        // 🧾 COLUMNA CENTRAL (INFO PRINCIPAL)
        Column(
            modifier = Modifier.weight(1f)
        ) {

            // 🔙 Botón back
            Text(
                text = "<",
                color = Color.White,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .padding(bottom = 16.dp)
            )

            Text(
                text = title.uppercase(),
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("DIRECTORES", color = Color.Gray)
            Text(directors, color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            Text("ACTORES", color = Color.Gray)
            Text(actors, color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            Text("SINOPSIS", color = Color.Gray)
            Text(description, color = Color.White)
        }

        // 📊 COLUMNA DERECHA (EXTRA INFO)
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column {
                Text("DURACIÓN", color = Color.Gray)
                Text(duration, color = Color.White)
            }

            Column {
                Text("CLASIFICACIÓN", color = Color.Gray)
                Text(ageRating, color = Color.White)
            }
        }
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