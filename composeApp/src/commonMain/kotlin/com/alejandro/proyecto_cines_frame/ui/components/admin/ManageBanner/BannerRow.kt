package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

@Composable
fun BannerRow(

    banner: BanerDTO,

    onEditBanner: (BanerDTO) -> Unit,

    onDeleteBanner: (BanerDTO) -> Unit

) {
    val isActive = isBannerActive(banner)
    val title = if (banner.peliculaId.isBlank()) "Pelicula" else "Pelicula ${banner.peliculaId}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),

        verticalAlignment =
            Alignment.CenterVertically,

        horizontalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        AsyncImage(
            model = banner.url,
            contentDescription = null,

            modifier = Modifier
                .width(140.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Text(
            text = title,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = banner.empieza,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = banner.termina,
            modifier = Modifier.weight(1f)
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

private fun isBannerActive(banner: BanerDTO): Boolean {
    val nowDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
    val startDate = runCatching { LocalDate.parse(banner.empieza) }.getOrNull() ?: return false
    val endDate = runCatching { LocalDate.parse(banner.termina) }.getOrNull() ?: return false
    return nowDate >= startDate && nowDate <= endDate
}
