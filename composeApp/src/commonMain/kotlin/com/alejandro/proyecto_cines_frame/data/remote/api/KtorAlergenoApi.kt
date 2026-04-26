package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.AlergenoApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.AlergenoDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorAlergenoApi(
    private val httpClient: HttpClient
) : AlergenoApi {

    private val baseUrl: String = getUrl().url + "/alergeno"

    /**
     * Devuelve todos los alérgenos
     */
    override suspend fun getAll(): List<AlergenoDTO> =
        httpClient.get(baseUrl).body()

    /**
     * Devuelve un alérgeno por el nombre buscado
     */
    override suspend fun getByNombre(nombre: String): AlergenoDTO =
        httpClient.get("$baseUrl/$nombre").body()

    /**
     * Crea un alérgeno si no existe
     */
    override suspend fun createAlergeno(alergeno: AlergenoDTO): AlergenoDTO =
        httpClient.post(baseUrl){
            contentType(ContentType.Application.Json)
            setBody(alergeno)
        }.body()

    /**
     * Actualiza un alérgeno
     */
    override suspend fun updateAlergeno(nombre: String, alergeno: AlergenoDTO): AlergenoDTO =
        httpClient.put("$baseUrl/$nombre"){
            contentType(ContentType.Application.Json)
            setBody(alergeno)
        }.body()

    /**
     * Elimina un alérgeno
     */
    override suspend fun deleteAlergeno(nombre: String): AlergenoDTO =
        httpClient.delete("$baseUrl/$nombre").body()
}