package com.alejandro.proyecto_cines_frame.ui.components.features.movies.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.alejandro.proyecto_cines_frame.ui.components.common.BackButton

@Composable
fun MovieDetailMovile(
    title: String,
    description: String,
    directors: String,
    actors: String,
    duration: String,
    ageRating: String,
    imagePainter: Painter,
    onBackClick: () -> Unit
) {

    val scrollState = rememberScrollState()

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

            ScheduleRow("3D", listOf("18:00", "21:00"))
            ScheduleRow("VOSE", listOf("17:30", "19:45"))

        }
        // 🔙 Botón back
        BackButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
    }
    /*
    // 🔙 Botón back
    BackButton(
        onClick = onBackClick
    )*/
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
