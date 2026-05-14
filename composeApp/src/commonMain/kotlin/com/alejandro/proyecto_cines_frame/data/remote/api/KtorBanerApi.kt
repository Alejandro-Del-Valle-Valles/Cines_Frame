package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.BanerApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorBanerApi(
    private val httpClient: HttpClient
): BanerApi {

    private val baseUrl: String = getUrl().url + "/baner"

    /**
     * Devuelve los banners para el actual día
     */
    override suspend fun getBanersToday(): List<BanerDTO> =
        httpClient.get(baseUrl).body()

    /**
     * Devuelve todos los banners
     */
    override suspend fun getAll(): List<BanerDTO> =
        httpClient.get("$baseUrl/all").body()


    /**
     * Crea un nuevo baner
     */
    override suspend fun createBaner(baner: BanerDTO): BanerDTO =
        httpClient.post(baseUrl){
            contentType(ContentType.Application.Json)
            setBody(baner)
        }.body()

    /**
     * Actualiza un baner en base a la url de su imagen
     */
    override suspend fun updateBaner(baner: BanerDTO): BanerDTO =
        httpClient.put(baseUrl){
            contentType(ContentType.Application.Json)
            setBody(baner)
        }.body()


    /**
     * Elimina un baner en base a la url de su imagen
     */
    override suspend fun deleteBaner(id: Int): BanerDTO {
        return httpClient.delete("$baseUrl/$id") {
            contentType(ContentType.Application.Json)
        }.body()
    }

}