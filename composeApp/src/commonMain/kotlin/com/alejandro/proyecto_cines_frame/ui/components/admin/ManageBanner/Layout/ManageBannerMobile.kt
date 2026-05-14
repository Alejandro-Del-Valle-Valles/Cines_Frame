package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner.*
import com.alejandro.proyecto_cines_frame.ui.theme.BackgroundDark
import com.alejandro.proyecto_cines_frame.ui.theme.OtroRojo

@Composable
fun ManageBannerMobile(

    state: BannerUiState,

    onAddBanner: () -> Unit,

    onEditBanner: (BanerDTO) -> Unit,

    onDeleteBanner: (BanerDTO) -> Unit

) {

    Scaffold(

        floatingActionButton = {

            FloatingActionButton(

                containerColor = OtroRojo,

                onClick = onAddBanner
            ) {

                Text("+")
            }
        }

    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(padding)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 90.dp,
                    bottom = 16.dp
                ),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            items(state.banners) { banner ->

                BannerCard(
                    banner = banner,
                    onEditBanner = onEditBanner,
                    onDeleteBanner = onDeleteBanner
                )
            }
        }
    }
}