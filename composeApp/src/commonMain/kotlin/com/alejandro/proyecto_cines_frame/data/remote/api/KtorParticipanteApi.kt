package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.ParticipanteApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorParticipanteApi(
    private val httpClient: HttpClient
) : ParticipanteApi {

    private val baseUrl: String = getUrl().url + "/participante"

    /**
     * Obtiene todos los pasticipantes
     */
    override suspend fun getAll(): List<ParticipanteDTO> = httpClient.get(baseUrl).body()

    /**
     * Obtiene todos los participantes por el nombre
     */
    override suspend fun getAllByNombre(nombre: String): List<ParticipanteDTO> =
        httpClient.get("$baseUrl/nombre/$nombre").body()

    /**
     * Obtiene un participante por su ID
     */
    override suspend fun getById(id: Int): ParticipanteDTO =
        httpClient.get("$baseUrl/id/$id").body()

    /**
     * Crea unuevo participante
     */
    override suspend fun createParticipante(nombre: String): ParticipanteDTO =
        httpClient.post("$baseUrl/$nombre").body()

    /**
     * Actualiza un participante
     */
    override suspend fun updateParticipante(participante: ParticipanteDTO): ParticipanteDTO =
        httpClient.put(baseUrl){
            contentType(ContentType.Application.Json)
            setBody(participante)
        }.body()

    /**
     * Elimina un participante
     */
    override suspend fun deleteParticipante(id: Int): ParticipanteDTO =
        httpClient.get("$baseUrl/$id").body()
}