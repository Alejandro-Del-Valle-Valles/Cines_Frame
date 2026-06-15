package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.data.remote.dto.CodigoDescuentoCreateDTO
import com.alejandro.proyecto_cines_frame.domain.enums.DescuentoCondicion
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.OutgoingContent
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json

class KtorCodigoDescuentoApiTest {

    @Test
    fun getAll_ok_devuelveListaYLlamaAlEndpointCorrecto() = runTest {
        var method: HttpMethod? = null
        var path: String? = null

        val client = buildClient { request ->
            method = request.method
            path = request.url.encodedPath

            respond(
                content = """
                    [
                      {
                        "id": 1,
                        "codigo": "PROMO10",
                        "condicion": "TODOS",
                        "porcentaje": 10,
                        "activo": true
                      },
                      {
                        "id": 2,
                        "codigo": "CINE5",
                        "condicion": "PELICULA",
                        "porcentaje": 5,
                        "activo": false
                      }
                    ]
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val api = KtorCodigoDescuentoApi(client)

        val response = api.getAll()

        assertEquals(HttpMethod.Get, method)
        assertEquals("/api/codigo-descuento", path)
        assertEquals(2, response.size)
        assertEquals("PROMO10", response[0].codigo)
        assertEquals(DescuentoCondicion.TODOS, response[0].condicion)
        assertEquals(10, response[0].porcentajeDescuento)
        assertEquals(true, response[0].activo)
    }

    @Test
    fun getById_ok_devuelveElementoYLlamaAlEndpointCorrecto() = runTest {
        var method: HttpMethod? = null
        var path: String? = null

        val client = buildClient { request ->
            method = request.method
            path = request.url.encodedPath

            respond(
                content = """
                    {
                      "id": 42,
                      "codigo": "ESTUDIANTE20",
                      "condicion": "TODOS",
                      "porcentaje": 20,
                      "activo": true
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val api = KtorCodigoDescuentoApi(client)

        val response = api.getById("42")

        assertEquals(HttpMethod.Get, method)
        assertEquals("/api/codigo-descuento/id/42", path)
        assertEquals(42, response.id)
        assertEquals("ESTUDIANTE20", response.codigo)
        assertEquals(DescuentoCondicion.TODOS, response.condicion)
        assertEquals(20, response.porcentajeDescuento)
        assertEquals(true, response.activo)
    }

    @Test
    fun getByCodigo_ok_devuelveElementoYLlamaAlEndpointCorrecto() = runTest {
        var method: HttpMethod? = null
        var path: String? = null

        val client = buildClient { request ->
            method = request.method
            path = request.url.encodedPath

            respond(
                content = """
                    {
                      "id": 7,
                      "codigo": "FESTIVO",
                      "condicion": "PELICULA",
                      "porcentaje": 15,
                      "activo": false
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val api = KtorCodigoDescuentoApi(client)

        val response = api.getByCodigo("FESTIVO")

        assertEquals(HttpMethod.Get, method)
        assertEquals("/api/codigo-descuento/codigo/FESTIVO", path)
        assertEquals(7, response.id)
        assertEquals("FESTIVO", response.codigo)
        assertEquals(DescuentoCondicion.PELICULA, response.condicion)
        assertEquals(15, response.porcentajeDescuento)
        assertEquals(false, response.activo)
    }

    @Test
    fun createCodigoDescuento_ok_enviaJsonYLlamaAlEndpointCorrecto() = runTest {
        var method: HttpMethod? = null
        var path: String? = null
        var bodyText: String? = null

        val client = buildClient { request ->
            method = request.method
            path = request.url.encodedPath
            bodyText = request.body.asText()

            respond(
                content = """
                    {
                      "id": 99,
                      "codigo": "NUEVO30",
                      "condicion": "TODOS",
                      "porcentaje": 30,
                      "activo": true
                    }
                """.trimIndent(),
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val api = KtorCodigoDescuentoApi(client)
        val dto = CodigoDescuentoCreateDTO(
            codigo = "NUEVO30",
            condicion = DescuentoCondicion.TODOS,
            porcentajeDescuento = 30,
            activo = true
        )

        val response = api.createCodigoDescuento(dto)

        assertEquals(HttpMethod.Post, method)
        assertEquals("/api/codigo-descuento", path)
        assertTrue(bodyText!!.isNotBlank())
        assertTrue(bodyText.contains("NUEVO30"))
        assertTrue(bodyText.contains("TODOS"))
        assertEquals(99, response.id)
        assertEquals("NUEVO30", response.codigo)
        assertEquals(DescuentoCondicion.TODOS, response.condicion)
        assertEquals(30, response.porcentajeDescuento)
        assertEquals(true, response.activo)
    }

    @Test
    fun updateCodigoDescuento_ok_enviaJsonYLlamaAlEndpointCorrecto() = runTest {
        var method: HttpMethod? = null
        var path: String? = null
        var bodyText: String? = null

        val client = buildClient { request ->
            method = request.method
            path = request.url.encodedPath
            bodyText = request.body.asText()

            respond(
                content = """
                    {
                      "id": 12,
                      "codigo": "ACTUALIZADO",
                      "condicion": "COMIDA",
                      "porcentaje": 12,
                      "activo": false
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val api = KtorCodigoDescuentoApi(client)
        val dto = CodigoDescuentoCreateDTO(
            codigo = "ACTUALIZADO",
            condicion = DescuentoCondicion.COMIDA,
            porcentajeDescuento = 12,
            activo = false
        )

        val response = api.updateCodigoDescuento(12, dto)

        assertEquals(HttpMethod.Put, method)
        assertEquals("/api/codigo-descuento/12", path)
        assertTrue(bodyText!!.isNotBlank())
        assertTrue(bodyText.contains("ACTUALIZADO"))
        assertTrue(bodyText.contains("COMIDA"))
        assertEquals(12, response.id)
        assertEquals("ACTUALIZADO", response.codigo)
        assertEquals(DescuentoCondicion.COMIDA, response.condicion)
        assertEquals(12, response.porcentajeDescuento)
        assertEquals(false, response.activo)
    }

    private fun buildClient(
        handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData
    ): HttpClient = HttpClient(MockEngine(handler)) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private fun OutgoingContent.asText(): String = when (this) {
        is OutgoingContent.ByteArrayContent -> bytes().decodeToString()
        is OutgoingContent.NoContent -> ""
        else -> error("Unexpected outgoing content type: ${this::class.qualifiedName}")
    }
}


