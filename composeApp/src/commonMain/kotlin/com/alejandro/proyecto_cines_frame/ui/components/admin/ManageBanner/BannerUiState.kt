package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageBanner

data class BannerUiState(

    val banners: List<BannerUiModel> = emptyList(),

    val isDialogVisible: Boolean = false,

    val editingBanner: BannerUiModel? = null,

    val form: BannerFormState = BannerFormState()
)