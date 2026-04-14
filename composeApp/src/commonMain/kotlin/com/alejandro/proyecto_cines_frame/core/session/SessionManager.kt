package com.alejandro.proyecto_cines_frame.core.session

import com.alejandro.proyecto_cines_frame.domain.model.Cuenta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SessionState(
    val isAuthenticated: Boolean = false,
    val cuenta: Cuenta? = null
)

object SessionManager {
    private val _state = MutableStateFlow(SessionState())
    val state: StateFlow<SessionState> = _state

    fun setSession(cuenta: Cuenta, token: String) {
        TokenStore.accessToken = token
        _state.value = SessionState(true, cuenta)
    }

    fun clearSession() {
        TokenStore.accessToken = null
        _state.value = SessionState(false, null)
    }
}