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
                .padding(16.dp),

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