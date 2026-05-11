package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO

@Composable
fun BannerTable(

    banners: List<BanerDTO>,

    onEditBanner: (BanerDTO) -> Unit,

    onDeleteBanner: (BanerDTO) -> Unit

) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Imagen",
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Título",
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Inicio",
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Fin",
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Estado",
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Acciones",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        HorizontalDivider()

        LazyColumn {

            items(banners) { banner ->

                BannerRow(
                    banner = banner,
                    onEditBanner = onEditBanner,
                    onDeleteBanner = onDeleteBanner
                )
            }
        }
    }
}