package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner.Layout.ManageBannerDesktop
import com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner.Layout.ManageBannerMobile

@Composable
fun ManageBanner(

    state: BannerUiState,

    onAddBanner: () -> Unit,

    onEditBanner: (BanerDTO) -> Unit,

    onDeleteBanner: (BanerDTO) -> Unit

) {

    BoxWithConstraints {

        if (
            ManageBannerUtils.esEscritorio(maxWidth)
        ) {

            ManageBannerDesktop(
                state = state,
                onAddBanner = onAddBanner,
                onEditBanner = onEditBanner,
                onDeleteBanner = onDeleteBanner
            )

        } else {

            ManageBannerMobile(
                state = state,
                onAddBanner = onAddBanner,
                onEditBanner = onEditBanner,
                onDeleteBanner = onDeleteBanner
            )
        }
    }
}