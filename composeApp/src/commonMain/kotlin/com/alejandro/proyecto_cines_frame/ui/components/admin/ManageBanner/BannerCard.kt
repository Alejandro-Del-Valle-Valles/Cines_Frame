package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun BannerCard(

    banner: BanerDTO,

    onEditBanner: (BanerDTO) -> Unit,

    onDeleteBanner: (BanerDTO) -> Unit

) {
    val isActive = isBannerActive(banner)
    val title = if (banner.peliculaId.isBlank()) "Pelicula" else "Pelicula ${banner.peliculaId}"

    Card(

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            AsyncImage(
                model = banner.url,
                contentDescription = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(
                text = title,

                color = TextWhite,

                style =
                    MaterialTheme.typography.titleMedium
            )

            Text(
                text =
                    "Inicio: ${banner.empieza}",

                color = TextWhite
            )

            Text(
                text =
                    "Fin: ${banner.termina}",

                color = TextWhite
            )

            Box(
                modifier = Modifier
                    .background(
                        if (isActive)
                            Color(0xFF22C55E).copy(alpha = 0.15f)
                        else
                            Color(0xFFEF4444).copy(alpha = 0.15f),

                        RoundedCornerShape(50)
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    )
            ) {

                Text(
                    text =
                        if (isActive)
                            "Activo"
                        else
                            "Inactivo",

                    color =
                        if (isActive)
                            Color(0xFF22C55E)
                        else
                            Color(0xFFEF4444)
                )
            }

            Row {

                TextButton(
                    onClick = {
                        onEditBanner(banner)
                    }
                ) {
                    Text("✏️")
                }

                TextButton(
                    onClick = {
                        onDeleteBanner(banner)
                    }
                ) {
                    Text("🗑️")
                }
            }
        }
    }
}

private fun isBannerActive(banner: BanerDTO): Boolean {
    val nowDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
    val startDate = runCatching { LocalDate.parse(banner.empieza) }.getOrNull() ?: return false
    val endDate = runCatching { LocalDate.parse(banner.termina) }.getOrNull() ?: return false
    return nowDate >= startDate && nowDate <= endDate
}
