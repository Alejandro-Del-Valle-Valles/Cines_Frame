package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.SalaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.SalaDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorSalaApi(
    private val httpClient: HttpClient
) : SalaApi {

    private val baseUrl: String = getUrl().url + "/sala"

    /**
     * Obtiene todas las salas
     */
    override suspend fun getAll(): List<SalaDTO> = httpClient.get(baseUrl).body()

    /**
     * Obtiene una sala por su número
     */
    override suspend fun getByNumero(numero: Int): SalaDTO = httpClient.get("$baseUrl/$numero").body()

    /**
     * Crea una nueva sala
     */
    override suspend fun createSala(sala: SalaDTO): SalaDTO =
        httpClient.post(baseUrl){
            contentType(ContentType.Application.Json)
            setBody(sala)
        }.body()

    /**
     * Actualiza una sala (Solo su aforo)
     */
    override suspend fun updateSala(sala: SalaDTO): SalaDTO =
        httpClient.put(baseUrl){
            contentType(ContentType.Application.Json)
            setBody(sala)
        }.body()

    /**
     * Elimina una sala
     */
    override suspend fun deleteSala(numero: Int): SalaDTO = httpClient.delete("$baseUrl/$numero").body()
}