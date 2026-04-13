package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.PeliculaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCompletoDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorPeliculaApi(
    private val httpClient: HttpClient
) : PeliculaApi {

    private val baseUrl: String = getUrl().url + "/pelicula"

    /**
     * Obtiene todas las películas con la info básica (Sin participantes)
     */
    override suspend fun getAllBasic(): List<PeliculaDTO> = httpClient.get(baseUrl).body()

    /**
     * Obtiene todas las películas con toda la info (Con los participantes)
     */
    override suspend fun getAllCompleto(): List<PeliculaCompletoDTO> = httpClient.get("$baseUrl/completo").body()

    /**
     * Obtiene todas las películas con la info básica por el nombre de la película (Sin participantes)
     */
    override suspend fun getAllBasicByNombre(nombre: String): List<PeliculaDTO> =
        httpClient.get("$baseUrl/basico/$nombre").body()

    /**
     * Obtiene todas las películas con toda la info epor el nombre de la película (Con participantes)
     */
    override suspend fun getAllCompletoByNombre(nombre: String): List<PeliculaCompletoDTO> =
        httpClient.get("$baseUrl/completo/$nombre").body()

    /**
     * Obtiene toda la info de la película por su id
     */
    override suspend fun getById(id: String): PeliculaCompletoDTO =
        httpClient.get("$baseUrl/id/$id").body()

    /**
     * Crea una nueva película con los participantes y sus roles
     */
    override suspend fun createPelicula(pelicula: PeliculaCreateDTO): PeliculaCompletoDTO =
        httpClient.post(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(pelicula)
        }.body()

    /**
     * Actualiza una película. Los participantes pasados son los que se settearan. Los anteriores serán desvinculados.
     */
    override suspend fun updatePelicula(id: String, pelicula: PeliculaCreateDTO): PeliculaCompletoDTO =
        httpClient.put("$baseUrl/$id"){
            contentType(ContentType.Application.Json)
            setBody(pelicula)
        }.body()

    /**
     * Elimina la película
     */
    override suspend fun deletePelicula(id: String): PeliculaDTO =
        httpClient.delete("$baseUrl/$id").body()
}