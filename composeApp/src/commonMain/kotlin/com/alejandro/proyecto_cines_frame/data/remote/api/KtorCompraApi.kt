package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.CompraApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CompraDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class KtorCompraApi(
    private val httpClient: HttpClient
) : CompraApi {

    private val baseUrl: String = getUrl().url + "/compra"

    /**
     * Devuelve el histŕoico de compras del usuario
     */
    override suspend fun getAll(): List<CompraDTO> = httpClient.get(baseUrl).body()

    /**
     * Devuelve el histórico de compras futuras (A suceder después de hoy)
     */
    override suspend fun getFuturas(): List<CompraDTO> = httpClient.get("$baseUrl/futuras").body()

    /**
     * Crea una compra y un usuario si no existe uno ya con el correo pasado
     */
    override suspend fun createCompra(compra: CompraDTO): CompraDTO =
        httpClient.post(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(compra)
        }.body()
}