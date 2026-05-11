package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner.*
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite

@Composable
fun ManageBannerDesktop(

    state: BannerUiState,

    onAddBanner: () -> Unit,

    onEditBanner: (BanerDTO) -> Unit,

    onDeleteBanner: (BanerDTO) -> Unit

) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),

        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    text = "Gestionar imágenes de banner",

                    color = TextWhite,

                    style =
                        MaterialTheme.typography.headlineMedium
                )

                Text(
                    text =
                        "Administra los banners de la página principal.",

                    color = TextWhite
                )
            }

            Button(
                onClick = onAddBanner
            ) {

                Text("Nuevo banner")
            }
        }

        Card(
            modifier = Modifier.fillMaxSize(),

            colors = CardDefaults.cardColors(
                containerColor = ColorFondoHeader
            )
        ) {

            BannerTable(
                banners = state.banners,
                onEditBanner = onEditBanner,
                onDeleteBanner = onDeleteBanner
            )
        }
    }
}