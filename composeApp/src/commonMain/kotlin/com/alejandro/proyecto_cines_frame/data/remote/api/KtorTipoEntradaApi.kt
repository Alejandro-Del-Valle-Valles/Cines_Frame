package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.TipoEntradaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CreateTipoEntrada
import com.alejandro.proyecto_cines_frame.data.remote.dto.TipoEntradaDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorTipoEntradaApi(
    private val httpClient: HttpClient
) : TipoEntradaApi {

    private val baseUrl: String = getUrl().url + "/tipo-entrada"

    /**
     * Devuelve todos los tipos de entrada
     */
    override suspend fun getAll(): List<TipoEntradaDTO> =
        httpClient.get(baseUrl).body()

    /**
     * Devuelve un tipo de entrada por el id buscado
     */
    override suspend fun getById(id: Int): TipoEntradaDTO =
        httpClient.get("$baseUrl/$id").body()

    /**
     * Crea un tipo de entrada nuevo
     */
    override suspend fun createTipoEntrada(tipoEntrada: CreateTipoEntrada): TipoEntradaDTO =
        httpClient.post(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(tipoEntrada)
        }.body()

    /**
     * Actualiza un tipo de entrada
     */
    override suspend fun updateTipoEntrada(tipoEntrada: TipoEntradaDTO): TipoEntradaDTO =
        httpClient.put(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(tipoEntrada)
        }.body()

    /**
     * Elimina un tipo de entrada
     */
    override suspend fun deleteTipoEntrada(id: Int): TipoEntradaDTO =
        httpClient.delete("$baseUrl/$id").body()
}