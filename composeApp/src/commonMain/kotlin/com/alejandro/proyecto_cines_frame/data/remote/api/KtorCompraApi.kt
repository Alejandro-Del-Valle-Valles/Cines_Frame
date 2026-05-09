package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.CompraApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CompraDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.timeout
import io.ktor.client.statement.HttpResponse
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class KtorCompraApi(
    private val httpClient: HttpClient
) : CompraApi {

    private val baseUrl: String = getUrl().url + "/compra"

    /**
     * Devuelve el histŕoico de compras del usuario
     */
    override suspend fun getAll(): List<CompraDTO> = httpClient.get("$baseUrl/todas").body()

    /**
     * Devuelve el histórico de compras futuras (A suceder después de hoy)
     */
    override suspend fun getFuturas(): List<CompraDTO> = httpClient.get("$baseUrl/futuras").body()

    /**
     * Crea una compra y un usuario si no existe uno ya con el correo pasado
     */
    override suspend fun createCompra(compra: CompraDTO): CompraDTO {
        val response = httpClient.post(baseUrl) {
            contentType(ContentType.Application.Json)
            setBody(compra)
            timeout {
                requestTimeoutMillis = 60_000
                socketTimeoutMillis = 60_000
            }
        }

        if (!response.status.isSuccess()) throw response.toStatusException()

        return runCatching { response.body<CompraDTO>() }
            .getOrElse { compra }
    }

    /**
     * Descarga el PDF de la entrada en formato ByteArray.
     */
    override suspend fun getCompraPdf(id: String): ByteArray {
        val response = httpClient.get("$baseUrl/$id/pdf") {
            header(HttpHeaders.Accept, "application/pdf")
        }
        val bytes = response.body<ByteArray>()
        if (response.status.isSuccess()) return bytes

        val contentType = response.headers[HttpHeaders.ContentType].orEmpty()
        val contentDisposition = response.headers[HttpHeaders.ContentDisposition].orEmpty()
        val looksLikePdf = bytes.size >= 4 &&
            bytes[0] == 0x25.toByte() && // %
            bytes[1] == 0x50.toByte() && // P
            bytes[2] == 0x44.toByte() && // D
            bytes[3] == 0x46.toByte()    // F
        val isPdfContentType = contentType.contains("application/pdf", ignoreCase = true)
        val isPdfDisposition = contentDisposition.contains(".pdf", ignoreCase = true)
        if (looksLikePdf || isPdfContentType || isPdfDisposition) {
            return bytes
        }

        throw response.toStatusException()
    }

}

private fun HttpResponse.toStatusException(): ResponseException {
    return when (status.value) {
        in 400..499 -> ClientRequestException(this, "Error HTTP ${status.value} al crear la compra")
        in 500..599 -> ServerResponseException(this, "Error HTTP ${status.value} al crear la compra")
        else -> ResponseException(this, "Respuesta HTTP no exitosa al crear la compra")
    }
}