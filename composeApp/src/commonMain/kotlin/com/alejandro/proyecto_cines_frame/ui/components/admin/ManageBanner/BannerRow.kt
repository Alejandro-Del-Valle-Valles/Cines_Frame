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

@Composable
fun BannerRow(

    banner: BannerUiModel,

    onEditBanner: (BannerUiModel) -> Unit,

    onDeleteBanner: (BannerUiModel) -> Unit

) {

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
            model = banner.imageUrl,
            contentDescription = null,

            modifier = Modifier
                .width(140.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Text(
            text = banner.titulo,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = banner.fechaInicio,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = banner.fechaFin,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .background(
                    if (banner.activo)
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
                    if (banner.activo)
                        "Activo"
                    else
                        "Inactivo",

                color =
                    if (banner.activo)
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