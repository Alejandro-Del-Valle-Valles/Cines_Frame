package com.alejandro.proyecto_cines_frame.ui.logic.presenter

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.session.SessionManager
import com.alejandro.proyecto_cines_frame.domain.extension.toFieldErrorMessagesIfAny
import com.alejandro.proyecto_cines_frame.domain.extension.toFirstUiMessagePerField
import com.alejandro.proyecto_cines_frame.domain.repository.CuentaRepository
import com.alejandro.proyecto_cines_frame.domain.validation.LoginValidator
import com.alejandro.proyecto_cines_frame.ui.logic.state.LoginUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginPresenter(
    private val cuentaRepo: CuentaRepository,
    private val scope: CoroutineScope
) {
    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state

    fun onCorreoChange(v: String) { _state.update { it.copy(correo = v) } }
    fun onContrasenaChange(v: String) { _state.update { it.copy(contrasena = v) } }
    fun consumeLoginSuccess() { _state.update { it.copy(loginSuccess = false) } }

    fun submit(rememberMe: Boolean) {
        val s = _state.value

        val errors = LoginValidator.validate(s.correo, s.contrasena)
        if (errors.values.any { it.isNotEmpty() }) {
            _state.update { it.copy(fieldErrors = errors.toFirstUiMessagePerField(), generalError = null) }
            return
        }

        scope.launch {
            _state.update { it.copy(isLoading = true, generalError = null, fieldErrors = emptyMap()) }

            when (val res = cuentaRepo.login(s.correo, s.contrasena, rememberMe)) {
                is ApiResult.Success -> {
                    SessionManager.setSession(res.data, res.data.token)
                    _state.update { it.copy(isLoading = false, loginSuccess = true) }
                }
                is ApiResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = false,
                            fieldErrors = res.error.toFieldErrorMessagesIfAny(),
                            generalError = res.error.toString()
                        )
                    }
                }
            }
        }
    }
}