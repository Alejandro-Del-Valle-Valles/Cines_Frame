package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldButacaRequest
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
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class KtorSesionApiTest {

    @Test
    fun createHoldToken_ok_devuelveTokenYLlamaAlEndpointCorrecto() = runTest {
        var method: HttpMethod? = null
        var path: String? = null

        val client = buildClient { request ->
            method = request.method
            path = request.url.encodedPath

            respond(
                content = """{"holdToken":"hold-123","expira":"2026-04-25T21:30:00"}""",
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val api = KtorSesionApi(client)

        val response = api.createHoldToken(
            numSala = 5,
            peliculaId = "pel-99",
            horario = "2026-04-25T21-30-00"
        )

        assertEquals(HttpMethod.Post, method)
        assertEquals("/api/sesion/5/pel-99/2026-04-25T21-30-00/hold-token", path)
        assertEquals("hold-123", response.holdToken)
        assertEquals("2026-04-25T21:30:00", response.expira)
    }

    @Test
    fun createHoldToken_ok_aceptaAliasTokenYPayloadEnvuelto() = runTest {
        val client = buildClient {
            respond(
                content = """{"data":{"token":"hold-xyz","expiresAt":"2026-04-25T22:00:00"}}""",
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val api = KtorSesionApi(client)

        val response = api.createHoldToken(
            numSala = 1,
            peliculaId = "pel-1",
            horario = "2026-04-25T22-00-00"
        )

        assertEquals("hold-xyz", response.holdToken)
        assertEquals("2026-04-25T22:00:00", response.expira)
    }

    @Test
    fun createHoldToken_fallaSiNoLlegaToken() = runTest {
        val client = buildClient {
            respond(
                content = """{"expira":"2026-04-25T21:30:00"}""",
                status = HttpStatusCode.Created,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val api = KtorSesionApi(client)

        assertFailsWith<SerializationException> {
            api.createHoldToken(
                numSala = 5,
                peliculaId = "pel-99",
                horario = "2026-04-25T21-30-00"
            )
        }
    }

    @Test
    fun releaseHoldToken_ok_devuelveStatusYLlamaAlEndpointCorrecto() = runTest {
        var method: HttpMethod? = null
        var path: String? = null
        var tokenParam: String? = null

        val client = buildClient { request ->
            method = request.method
            path = request.url.encodedPath
            tokenParam = request.url.parameters["token"]

            respond(
                content = "",
                status = HttpStatusCode.NoContent
            )
        }

        val api = KtorSesionApi(client)

        val status = api.releaseHoldToken(
            numSala = 5,
            peliculaId = "pel-99",
            horario = "2026-04-25T21-30-00",
            token = "hold-123"
        )

        assertEquals(HttpMethod.Delete, method)
        assertEquals("/api/sesion/5/pel-99/2026-04-25T21-30-00/hold-token", path)
        assertEquals("hold-123", tokenParam)
        assertEquals(HttpStatusCode.NoContent, status)
    }

    @Test
    fun holdButaca_ok_devuelveStatusYLlamaAlEndpointCorrecto() = runTest {
        var method: HttpMethod? = null
        var path: String? = null
        var contentType: String? = null

        val client = buildClient { request ->
            method = request.method
            path = request.url.encodedPath
            contentType = request.headers[HttpHeaders.ContentType]

            respond(
                content = "",
                status = HttpStatusCode.OK
            )
        }

        val api = KtorSesionApi(client)

        val status = api.holdButaca(
            numSala = 2,
            peliculaId = "pel-10",
            horario = "2026-04-26T19-00-00",
            req = HoldButacaRequest(token = "token-abc", fila = 4, butaca = 7)
        )

        assertEquals(HttpMethod.Post, method)
        assertEquals("/api/sesion/2/pel-10/2026-04-26T19-00-00/butaca/hold", path)
        assertEquals(contentType?.contains("application/json"), null)
        assertEquals(HttpStatusCode.OK, status)
    }

    @Test
    fun holdButaca_conflict_propagaElStatusDelServidor() = runTest {
        val client = buildClient {
            respond(
                content = "",
                status = HttpStatusCode.Conflict
            )
        }

        val api = KtorSesionApi(client)

        val status = api.holdButaca(
            numSala = 2,
            peliculaId = "pel-10",
            horario = "2026-04-26T19-00-00",
            req = HoldButacaRequest(token = "token-abc", fila = 4, butaca = 7)
        )

        assertEquals(HttpStatusCode.Conflict, status)
    }

    @Test
    fun releaseButaca_ok_devuelveStatusYLlamaAlEndpointCorrecto() = runTest {
        var method: HttpMethod? = null
        var path: String? = null
        var contentType: String? = null

        val client = buildClient { request ->
            method = request.method
            path = request.url.encodedPath
            contentType = request.headers[HttpHeaders.ContentType]

            respond(
                content = "",
                status = HttpStatusCode.NoContent
            )
        }

        val api = KtorSesionApi(client)

        val status = api.releaseButaca(
            numSala = 2,
            peliculaId = "pel-10",
            horario = "2026-04-26T19-00-00",
            req = HoldButacaRequest(token = "token-abc", fila = 4, butaca = 7)
        )

        assertEquals(HttpMethod.Delete, method)
        assertEquals("/api/sesion/2/pel-10/2026-04-26T19-00-00/butaca/hold", path)
        assertEquals(contentType?.contains("application/json"), null)
        assertEquals(HttpStatusCode.NoContent, status)
    }

    @Test
    fun releaseButaca_locked_propagaElStatusDelServidor() = runTest {
        val client = buildClient {
            respond(
                content = "",
                status = HttpStatusCode.Locked
            )
        }

        val api = KtorSesionApi(client)

        val status = api.releaseButaca(
            numSala = 2,
            peliculaId = "pel-10",
            horario = "2026-04-26T19-00-00",
            req = HoldButacaRequest(token = "token-abc", fila = 4, butaca = 7)
        )

        assertEquals(HttpStatusCode.Locked, status)
    }

    private fun buildClient(
        handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData
    ): HttpClient = HttpClient(MockEngine(handler)) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}

