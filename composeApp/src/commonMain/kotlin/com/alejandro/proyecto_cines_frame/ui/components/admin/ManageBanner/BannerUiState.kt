package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner

import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO

data class BannerUiState(

    val banners: List<BanerDTO> = emptyList(),

    val isDialogVisible: Boolean = false,

    val editingBanner: BanerDTO? = null,

    val form: BannerFormState = BannerFormState()
)