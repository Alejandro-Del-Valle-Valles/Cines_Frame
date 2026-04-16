package com.alejandro.proyecto_cines_frame.ui.logic.state

data class LoginUiState(
    val correo: String = "",
    val contrasena: String = "",
    val fieldErrors: Map<String, String> = emptyMap(), // mensajes listos para UI
    val isLoading: Boolean = false,
    val generalError: String? = null,
    val loginSuccess: Boolean = false
)
