package com.alejandro.proyecto_cines_frame.ui.logic.state

data class ProfileUiState(
    val currentName: String = "",
    val newName: String = "",
    val currentPasswordForName: String = "",
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmNewPassword: String = "",
    val fieldErrors: Map<String, String> = emptyMap(),
    val generalError: String? = null,
    val isLoading: Boolean = false,
    val nameUpdated: Boolean = false,
    val passwordUpdated: Boolean = false
)

