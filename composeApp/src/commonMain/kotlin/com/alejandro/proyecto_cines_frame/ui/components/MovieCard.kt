// ui/components/MovieCard.kt
package com.alejandro.proyecto_cines_frame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Movie

/** Tarjeta de película que muestra la portada (placeholder) y el título. */
@Composable

fun MovieCard(movie: Movie, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(110.dp)
    ) {
        // Placeholder para la imagen de portada de la película
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.Gray, RoundedCornerShape(12.dp))
        ) {
            // Aquí se podría cargar la imagen real si se tiene (e.g. con Coil o AsyncImage)
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Título de la película
        Text(
            text = movie.title,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}