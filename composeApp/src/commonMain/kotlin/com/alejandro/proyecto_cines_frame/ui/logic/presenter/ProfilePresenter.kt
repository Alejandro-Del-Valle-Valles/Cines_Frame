package com.alejandro.proyecto_cines_frame.ui.logic.presenter

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.session.SessionManager
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaUpdateDTO
import com.alejandro.proyecto_cines_frame.domain.extension.toFieldErrorMessagesIfAny
import com.alejandro.proyecto_cines_frame.domain.extension.toFirstUiMessagePerField
import com.alejandro.proyecto_cines_frame.domain.model.Cuenta
import com.alejandro.proyecto_cines_frame.domain.repository.CuentaRepository
import com.alejandro.proyecto_cines_frame.domain.validation.CuentaValidator
import com.alejandro.proyecto_cines_frame.domain.validation.FieldError
import com.alejandro.proyecto_cines_frame.domain.validation.FieldErrors
import com.alejandro.proyecto_cines_frame.ui.logic.state.ProfileUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfilePresenter(
    private val cuentaRepo: CuentaRepository,
    private val scope: CoroutineScope
) {
    private val _state = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state

    private var currentCuenta: Cuenta? = null

    fun setCuenta(cuenta: Cuenta?) {
        currentCuenta = cuenta
        _state.update {
            it.copy(
                currentName = cuenta?.nombre ?: "",
                newName = cuenta?.nombre ?: ""
            )
        }
    }

    fun onNewNameChange(v: String) { _state.update { it.copy(newName = v) } }
    fun onCurrentPasswordForNameChange(v: String) { _state.update { it.copy(currentPasswordForName = v) } }
    fun onCurrentPasswordChange(v: String) { _state.update { it.copy(currentPassword = v) } }
    fun onNewPasswordChange(v: String) { _state.update { it.copy(newPassword = v) } }
    fun onConfirmNewPasswordChange(v: String) { _state.update { it.copy(confirmNewPassword = v) } }

    fun consumeNameUpdated() { _state.update { it.copy(nameUpdated = false) } }
    fun consumePasswordUpdated() { _state.update { it.copy(passwordUpdated = false) } }

    fun validateNameInputs(): Boolean {
        val cuenta = currentCuenta ?: run {
            _state.update { it.copy(generalError = "Debes iniciar sesion para actualizar tu cuenta.") }
            return false
        }
        val errors = buildNameErrors(cuenta)
        if (errors.values.any { it.isNotEmpty() }) {
            _state.update { it.copy(fieldErrors = errors.toFirstUiMessagePerField(), generalError = null) }
            return false
        }
        return true
    }

    fun validatePasswordInputs(): Boolean {
        if (currentCuenta == null) {
            _state.update { it.copy(generalError = "Debes iniciar sesion para actualizar tu cuenta.") }
            return false
        }
        val errors = buildPasswordErrors()
        if (errors.values.any { it.isNotEmpty() }) {
            _state.update { it.copy(fieldErrors = errors.toFirstUiMessagePerField(), generalError = null) }
            return false
        }
        return true
    }

    fun submitNameChange(rememberMe: Boolean) {
        val cuenta = currentCuenta ?: run {
            _state.update { it.copy(generalError = "Debes iniciar sesion para actualizar tu cuenta.") }
            return
        }
        if (!validateNameInputs()) return

        val s = _state.value
        val update = CuentaUpdateDTO(
            nombre = s.newName.trim(),
            contrasena = s.currentPasswordForName,
            rol = cuenta.rol
        )

        scope.launch {
            _state.update { it.copy(isLoading = true, generalError = null, fieldErrors = emptyMap()) }

            when (val res = cuentaRepo.updateCuenta(update, cuenta.usuario.correo, rememberMe)) {
                is ApiResult.Success -> {
                    currentCuenta = res.data
                    SessionManager.setSession(res.data, res.data.token)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            nameUpdated = true,
                            currentName = res.data.nombre,
                            newName = res.data.nombre,
                            currentPasswordForName = ""
                        )
                    }
                }
                is ApiResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            fieldErrors = res.error.toFieldErrorMessagesIfAny(),
                            generalError = res.error.toString()
                        )
                    }
                }
            }
        }
    }

    fun submitPasswordChange(rememberMe: Boolean) {
        val cuenta = currentCuenta ?: run {
            _state.update { it.copy(generalError = "Debes iniciar sesion para actualizar tu cuenta.") }
            return
        }
        if (!validatePasswordInputs()) return

        val s = _state.value
        val update = CuentaUpdateDTO(
            nombre = cuenta.nombre,
            contrasena = s.newPassword,
            rol = cuenta.rol
        )

        scope.launch {
            _state.update { it.copy(isLoading = true, generalError = null, fieldErrors = emptyMap()) }

            when (val res = cuentaRepo.updateCuenta(update, cuenta.usuario.correo, rememberMe)) {
                is ApiResult.Success -> {
                    currentCuenta = res.data
                    _state.update {
                        it.copy(
                            isLoading = false,
                            passwordUpdated = true,
                            currentPassword = "",
                            newPassword = "",
                            confirmNewPassword = ""
                        )
                    }
                }
                is ApiResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            fieldErrors = res.error.toFieldErrorMessagesIfAny(),
                            generalError = res.error.toString()
                        )
                    }
                }
            }
        }
    }

    private fun buildNameErrors(cuenta: Cuenta): FieldErrors {
        val errors = CuentaValidator.validateNewName(cuenta.nombre, _state.value.newName).toMutableMap()
        if (_state.value.currentPasswordForName.isBlank()) {
            errors["contrasenaActualNombre"] = listOf(FieldError.Required("contrasenaActualNombre"))
        }
        return errors
    }

    private fun buildPasswordErrors(): FieldErrors {
        val errors = CuentaValidator.validateNewPassword(
            currentPassword = _state.value.currentPassword,
            newPassword = _state.value.newPassword,
            confirmNewPassword = _state.value.confirmNewPassword
        ).toMutableMap()
        if (_state.value.currentPassword.isBlank()) {
            errors["contrasenaActual"] = listOf(FieldError.Required("contrasenaActual"))
        }
        return errors
    }
}
