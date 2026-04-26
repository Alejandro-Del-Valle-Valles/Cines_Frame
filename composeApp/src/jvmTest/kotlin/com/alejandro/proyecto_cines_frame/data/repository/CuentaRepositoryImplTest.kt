package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.security.SecureCredentialStore
import com.alejandro.proyecto_cines_frame.core.session.TokenStore
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.CuentaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaLoginDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaUpdateDTO
import com.alejandro.proyecto_cines_frame.domain.enums.CuentaRol
import com.alejandro.proyecto_cines_frame.domain.model.Cuenta
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlinx.coroutines.test.runTest

class CuentaRepositoryImplTest {

    private val api: CuentaApi = mockk()
    private val secureStore: SecureCredentialStore = mockk()

    private val repository = CuentaRepositoryImpl(
        api = api,
        secureStore = secureStore
    )

    @AfterTest
    fun tearDown() {
        TokenStore.accessToken = null
    }

    @Test
    fun login_success_setsToken_and_savesCredentials_whenRememberMe() = runTest {
        // Arrange
        val correo = "a@a.com"
        val password = "1234"
        val token = "token-abc"

        coEvery { api.login(match { it.correo == correo && it.contrasena == password }) } returns
            CuentaLoginDTO(
                token = token,
                correo = correo,
                nombre = "Alex",
                rol = CuentaRol.CLIENTE
            )
        coEvery { secureStore.save(correo, password) } just runs

        // Act
        val result = repository.login(correo, password, rememberMe = true)

        // Assert
        assertIs<ApiResult.Success<*>>(result)
        assertEquals(token, TokenStore.accessToken)
        coVerify(exactly = 1) { secureStore.save(correo, password) }
    }

    @Test
    fun login_success_setsToken_and_doesNotSaveCredentials_whenNotRememberMe() = runTest {
        // Arrange
        val correo = "b@b.com"
        val password = "pass"
        val token = "token-xyz"

        coEvery { api.login(match { it.correo == correo && it.contrasena == password }) } returns
            CuentaLoginDTO(
                token = token,
                correo = correo,
                nombre = "Bea",
                rol = CuentaRol.CLIENTE
            )

        // Act
        val result = repository.login(correo, password, rememberMe = false)

        // Assert
        assertIs<ApiResult.Success<*>>(result)
        assertEquals(token, TokenStore.accessToken)
        coVerify(exactly = 0) { secureStore.save(any(), any()) }
    }

    @Test
    fun createCuenta_success_returnsSuccess() = runTest {
        // Arrange
        val input = CuentaDTO(
            correo = "new@user.com",
            contrasena = "pwd",
            nombre = "Nuevo",
            rol = CuentaRol.CLIENTE
        )
        coEvery { api.createCuenta(input) } returns input

        // Act
        val result = repository.createCuenta(input)

        // Assert
        assertIs<ApiResult.Success<*>>(result)
        coVerify(exactly = 1) { api.createCuenta(input) }
    }

    @Test
    fun updateCuenta_success_updatesToken_and_remembersCredentials_whenRequested() = runTest {
        // Arrange
        val update = CuentaUpdateDTO(
            nombre = "Alex Renovado",
            contrasena = "new-password",
            rol = CuentaRol.ADMINISTRADOR
        )
        val updatedCuenta = CuentaDTO(
            correo = "old@user.com",
            nombre = update.nombre,
            contrasena = update.contrasena,
            rol = update.rol
        )
        val loginDto = CuentaLoginDTO(
            token = "new-token",
            correo = "old@user.com",
            nombre = update.nombre,
            rol = update.rol
        )
        coEvery { api.updateCuenta(update) } returns updatedCuenta
        coEvery { api.login(match { it.correo == "old@user.com" && it.contrasena == update.contrasena }) } returns loginDto
        coEvery { secureStore.save("old@user.com", update.contrasena) } just runs

        // Act
        val result = repository.updateCuenta(
            cuentaUpdate = update,
            correoActual = "old@user.com",
            rememberMe = true
        )

        // Assert
        assertIs<ApiResult.Success<*>>(result)
        val cuenta = (result as ApiResult.Success<*>).data as Cuenta
        assertEquals("old@user.com", cuentaField(cuenta, "usuario")?.let { usuarioCorreo(it) })
        assertEquals("new-password", cuentaField(cuenta, "contrasena"))
        assertEquals("Alex Renovado", cuentaField(cuenta, "nombre"))
        assertEquals(CuentaRol.ADMINISTRADOR, cuentaField(cuenta, "rol"))
        assertEquals("new-token", cuentaField(cuenta, "token"))
        assertEquals("new-token", TokenStore.accessToken)
        coVerify(exactly = 1) { api.updateCuenta(update) }
        coVerify(exactly = 1) { api.login(any()) }
        coVerify(exactly = 1) { secureStore.save("old@user.com", update.contrasena) }
    }

    @Test
    fun logout_success_clearsToken_and_optionallyClearsRememberedCredentials() = runTest {
        // Arrange
        TokenStore.accessToken = "already-set"
        every { api.logout() } just runs
        coEvery { secureStore.clear() } just runs

        // Act
        val result = repository.logout(clearRememberedCredentials = true)

        // Assert
        assertIs<ApiResult.Success<Unit>>(result)
        assertNull(TokenStore.accessToken)
        coVerify(exactly = 1) { secureStore.clear() }
    }

    @Test
    fun logout_success_clearsToken_and_doesNotClearRememberedCredentials_ifFalse() = runTest {
        // Arrange
        TokenStore.accessToken = "already-set"
        every { api.logout() } just runs

        // Act
        val result = repository.logout(clearRememberedCredentials = false)

        // Assert
        assertIs<ApiResult.Success<Unit>>(result)
        assertNull(TokenStore.accessToken)
        coVerify(exactly = 0) { secureStore.clear() }
    }

    private fun cuentaField(cuenta: Cuenta, fieldName: String): Any? {
        val field = cuenta.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(cuenta)
    }

    private fun usuarioCorreo(usuario: Any): Any? {
        val field = usuario.javaClass.getDeclaredField("correo")
        field.isAccessible = true
        return field.get(usuario)
    }
}

