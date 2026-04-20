package com.alejandro.proyecto_cines_frame.ui.logic.state

data class RegisterUiState(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val fieldErrors: Map<String, String> = emptyMap(), // mensajes listos para UI
    val isLoading: Boolean = false,
    val generalError: String? = null,
    val registerSuccess: Boolean = false
)