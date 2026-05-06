package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.session.TokenStore
import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.CuentaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaUpdateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LoginDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.core.security.SecureCredentialStore
import com.alejandro.proyecto_cines_frame.data.adapter.toDomain
import com.alejandro.proyecto_cines_frame.domain.model.Cuenta
import com.alejandro.proyecto_cines_frame.domain.repository.CuentaRepository

class CuentaRepositoryImpl(
    private val api: CuentaApi,
    private val secureStore: SecureCredentialStore
) : CuentaRepository {

    /**
     * Loguea al usuario y guarda el token que le da acceso a la API.
     * Tambien guarda de forma segura sus credenciales para autologuearse.
     */
    override suspend fun login(correo: String, password: String, rememberMe: Boolean): ApiResult<Cuenta> {
        return try {
            val loginDto = api.login(LoginDTO(correo = correo, contrasena = password))
            TokenStore.accessToken = loginDto.token
            val cuenta = loginDto.toDomain()
            if (rememberMe) secureStore.save(correo, password)
            ApiResult.Success(cuenta)
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Loguea al usuario de forma automatica si ya se ha logueado antes.
     */
    override suspend fun autoLoginIfPossible(): ApiResult<Cuenta?> {
        return try {
            val creds = secureStore.read()
            if (creds == null) {
                ApiResult.Success(null)
            } else {
                val loginDto = api.login(LoginDTO(correo = creds.correo, contrasena = creds.password))
                TokenStore.accessToken = loginDto.token
                ApiResult.Success(loginDto.toDomain())
            }
        } catch (t: Throwable) {
            // si falló auto-login, limpiar credenciales obsoletas
            secureStore.clear()
            TokenStore.accessToken = null
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Registra una nueva cuenta para el ususario
     */
    override suspend fun createCuenta(cuenta: CuentaDTO): ApiResult<Cuenta> {
        return try {
            val dto = api.createCuenta(cuenta)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Actualiza la cuenta del usuario
     */
    override suspend fun updateCuenta(
        cuentaUpdate: CuentaUpdateDTO,
        correoActual: String,
        rememberMe: Boolean
    ): ApiResult<Cuenta> {
        return try {
            val dto = api.updateCuenta(cuentaUpdate)
            val cuenta = dto.toDomain()

            if (rememberMe) {
                secureStore.save(correoActual, cuentaUpdate.contrasena)
            }

            val loginDto = api.login(LoginDTO(correoActual, cuentaUpdate.contrasena))
            TokenStore.accessToken = loginDto.token

            ApiResult.Success(
                Cuenta(
                    usuario = cuenta.usuario,
                    contrasena = cuenta.contrasena,
                    nombre = cuenta.nombre,
                    rol = cuenta.rol,
                    token = loginDto.token
                )
            )
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Cierra sesón y borra el token y las credenciales.
     */
    override suspend fun logout(clearRememberedCredentials: Boolean): ApiResult<Unit> {
        return try {
            api.logout()
            TokenStore.accessToken = null

            if (clearRememberedCredentials) {
                secureStore.clear()
            }

            ApiResult.Success(Unit)
        } catch (t: Throwable) {
            TokenStore.accessToken = null
            if (clearRememberedCredentials) secureStore.clear()
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Elimina la cuenta del usuario y borra el token y sus credenciales
     */
    override suspend fun deleteCuenta(): ApiResult<Cuenta> {
        return try {
            val dto = api.deleteCuenta()
            TokenStore.accessToken = null
            secureStore.clear()

            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }
}