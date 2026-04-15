package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaUpdateDTO
import com.alejandro.proyecto_cines_frame.domain.model.Cuenta

interface CuentaRepository {
    suspend fun login(correo: String, password: String, rememberMe: Boolean): ApiResult<Cuenta>
    suspend fun autoLoginIfPossible(): ApiResult<Cuenta?>
    suspend fun createCuenta(cuenta: CuentaDTO) : ApiResult<Cuenta>
    suspend fun updateCuenta(
        cuentaUpdate: CuentaUpdateDTO,
        correoActual: String,
        rememberMe: Boolean
    ): ApiResult<Cuenta>
    suspend fun logout(clearRememberedCredentials: Boolean): ApiResult<Unit>
    suspend fun deleteCuenta() : ApiResult<Cuenta>
}