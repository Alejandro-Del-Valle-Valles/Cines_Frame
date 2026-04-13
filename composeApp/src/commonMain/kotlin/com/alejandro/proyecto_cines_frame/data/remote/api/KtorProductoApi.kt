package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.ProductoApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.ProductoDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorProductoApi(
    private val httpClient: HttpClient
) : ProductoApi {

    private val baseUrl: String = getUrl().url + "/producto"

    /**
     * Devuelve todos los productos con sus alérgenos
     */
    override suspend fun getAll(): List<ProductoDTO> = httpClient.get(baseUrl).body()

    /**
     * Obtiene un producto por su nombre
     */
    override suspend fun getByNombre(nombre: String): ProductoDTO = httpClient.get("$baseUrl/$nombre").body()

    /**
     * Crea un nuevo producto
     */
    override suspend fun createProducto(producto: ProductoDTO): ProductoDTO =
        httpClient.post(baseUrl){
            contentType(ContentType.Application.Json)
            setBody(producto)
        }.body()

    /**
     * Actualiza el producto
     */
    override suspend fun updateProducto(
        nombre: String,
        producto: ProductoDTO
    ): ProductoDTO =
        httpClient.put("$baseUrl/$nombre"){
            contentType(ContentType.Application.Json)
            setBody(producto)
        }.body()

    /**
     * Elimina el producto
     */
    override suspend fun deleteProducto(nombre: String): ProductoDTO = httpClient.delete("$baseUrl/$nombre").body()
}