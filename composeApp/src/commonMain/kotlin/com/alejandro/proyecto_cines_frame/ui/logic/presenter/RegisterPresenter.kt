package com.alejandro.proyecto_cines_frame.ui.logic.presenter

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.error.AppError
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaDTO
import com.alejandro.proyecto_cines_frame.domain.enums.CuentaRol
import com.alejandro.proyecto_cines_frame.domain.extension.toFieldErrorMessagesIfAny
import com.alejandro.proyecto_cines_frame.domain.extension.toFirstUiMessagePerField
import com.alejandro.proyecto_cines_frame.domain.repository.CuentaRepository
import com.alejandro.proyecto_cines_frame.domain.validation.RegisterValidator
import com.alejandro.proyecto_cines_frame.ui.logic.state.RegisterUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterPresenter(
    private val cuentaRepo: CuentaRepository,
    private val scope: CoroutineScope
) {
    private val _state = MutableStateFlow(RegisterUiState())
    val state: StateFlow<RegisterUiState> = _state

    fun onNombreChange(v: String) { _state.update { it.copy(nombre = v) } }
    fun onCorreoChange(v: String) { _state.update { it.copy(correo = v) } }
    fun onContrasenaChange(v: String) { _state.update { it.copy(contrasena = v) } }
    fun onConfirmarContrasenaChange(v: String) { _state.update { it.copy(confirmarContrasena = v) } }
    fun consumeRegisterSuccess() { _state.update { it.copy(registerSuccess = false) } }

    fun submit(rememberMe: Boolean) {
        val s = _state.value

        val errors = RegisterValidator.validate(
            nombre = s.nombre,
            correo = s.correo,
            contrasena = s.contrasena,
            confirmarContrasena = s.confirmarContrasena
        )
        if (errors.values.any { it.isNotEmpty() }) {
            _state.update { it.copy(fieldErrors = errors.toFirstUiMessagePerField(), generalError = null) }
            return
        }

        scope.launch {
            _state.update { it.copy(isLoading = true, generalError = null, fieldErrors = emptyMap()) }

            val cuentaCreate = CuentaDTO(
                nombre = s.nombre,
                correo = s.correo,
                contrasena = s.contrasena,
                rol = CuentaRol.CLIENTE
            )
            when (val res = cuentaRepo.createCuenta(cuentaCreate)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false, registerSuccess = true) }
                }
                is ApiResult.Error -> {
                    val errorMsg = if (res.error is AppError.Conflict) {
                        res.error.details["message"] ?: "Ya existe una cuenta con el correo indicado"
                    } else {
                        "Error desconocido, es probable que ya exista la cuenta."
                    }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            registerSuccess = false,
                            fieldErrors = res.error.toFieldErrorMessagesIfAny(),
                            generalError = errorMsg
                        )
                    }
                }
            }
        }
    }
}
