package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.CodigoDescuentoApi
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.PeliculaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CodigoDescuentoCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CodigoDescuentoDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorCodigoDescuentoApi(
    private val httpClient: HttpClient
) : CodigoDescuentoApi {

    private val baseUrl: String = getUrl().url + "/descuento"

    /**
     * Obtiene todos los codigos de descuento
     */
    override suspend fun getAll(): List<CodigoDescuentoDTO> = httpClient.get(baseUrl).body()

    /**
     * Obtiene un codigo de descuento por su id
     */
    override suspend fun getById(id: String): CodigoDescuentoDTO = httpClient.get("$baseUrl/id/$id").body()

    /**
     * Obtiene un codigo de descuento por su codigo
     */
    override suspend fun getByCodigo(id: String): CodigoDescuentoDTO = httpClient.get("$baseUrl/codigo/$id").body()

    /**
     * Crea un nuevo codigo de descuento
     */
    override suspend fun createCodigoDescuento(codigoDescuento: CodigoDescuentoCreateDTO): CodigoDescuentoDTO =
        httpClient.put(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(codigoDescuento)
        }.body()

    /**
     * Actualiza un codigo de descuendo con los nuevos datos.
     */
    override suspend fun updateCodigoDescuento(id: Int, codigoDescuento: CodigoDescuentoCreateDTO): CodigoDescuentoDTO =
        httpClient.put("$baseUrl/$id"){
            contentType(ContentType.Application.Json)
            setBody(codigoDescuento)
        }.body()

}