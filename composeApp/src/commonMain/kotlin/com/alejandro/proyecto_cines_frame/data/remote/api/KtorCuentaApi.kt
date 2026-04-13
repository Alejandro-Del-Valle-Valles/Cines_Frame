package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.session.TokenStore
import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.CuentaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaLoginDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CuentaUpdateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LoginDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorCuentaApi(
    private val httpClient: HttpClient
) : CuentaApi {
    private val baseUrl: String = getUrl().url + "/cuenta"

    /**
     * Loguea al usuario en el servidor y le da un token con una duración específica
     */
    override suspend fun login(login: LoginDTO): CuentaLoginDTO =
        httpClient.post("$baseUrl/login") {
            contentType(ContentType.Application.Json)
            setBody(login)
        }.body()

    /**
     * Cierra la sesión del usuario en el cliente, borra sus credenciales en cliente.
     */
    override fun logout() {
        TokenStore.accessToken = ""
    }

    /**
     * Regsitra una nueva cuenta. Si no existe un usuario con el correo pasado, lo genera.
     */
    override suspend fun createCuenta(cuenta: CuentaDTO): CuentaDTO =
        httpClient.post("$baseUrl/registro"){
            contentType(ContentType.Application.Json)
            setBody(cuenta)
        }.body()

    /**
     * Actualiza la cuenta con los nuevos datos.
     */
    override suspend fun updateCuenta(cuenta: CuentaUpdateDTO): CuentaDTO =
        httpClient.put("$baseUrl/me"){
            contentType(ContentType.Application.Json)
            setBody(cuenta)
        }.body()

    /**
     * Elimina la cuenta
     */
    override suspend fun deleteCuenta(): CuentaDTO =
        httpClient.delete("$baseUrl/me").body()

}