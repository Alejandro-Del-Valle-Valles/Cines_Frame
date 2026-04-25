package com.alejandro.proyecto_cines_frame.data.remote.api

import com.alejandro.proyecto_cines_frame.core.network.getUrl
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.SesionApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.ButacasStatusResponse
import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldButacaRequest
import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldTokenResponse
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCompletaDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCrudDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class KtorSesionApi(
    private val httpClient: HttpClient
) : SesionApi {
    private val baseUrl: String = getUrl().url + "/sesion"

    /**
     * Devuelve todas las sesiones
     */
    override suspend fun getAll(): List<SesionCompletaDTO> = httpClient.get(baseUrl).body()

    /**
     * Devuelve todas las sesiones en un rango horario
     */
    override suspend fun getByRangoHorario(
        inicio: String,
        fin: String
    ): List<SesionCompletaDTO> = httpClient.get("$baseUrl/horario?inicio=$inicio&fin=$fin").body()

    /**
     * Devuelve una sesión en concreto
     */
    override suspend fun getSesion(
        numSala: Int,
        peliculaId: String,
        horario: String
    ): SesionCompletaDTO = httpClient.get("$baseUrl/sesion?numSala=$numSala&peliculaId=$peliculaId&horario=$horario").body()

    /**
     * Crea una sesión
     */
    override suspend fun createSesion(sesion: SesionCrudDTO): SesionCompletaDTO =
        httpClient.post(baseUrl){
            contentType(ContentType.Application.Json)
            setBody(sesion)
        }.body()

    /**
     * Elimina una sesión
     */
    override suspend fun deleteSesion(sesion: SesionCrudDTO): SesionCompletaDTO =
        httpClient.delete(baseUrl){
            contentType(ContentType.Application.Json)
            setBody(sesion)
        }.body()

    /**
     * Crea un token con una duración para poder reservar butacas de una sesión concreta
     */
    override suspend fun createHoldToken(numSala: Int, peliculaId: String, horario: String): HoldTokenResponse =
        httpClient.post("$baseUrl/$numSala/$peliculaId/$horario/hold-token").body()

    /**
     * Reserva una butaca de una sesión cocnreta al seleccionarla
     */
    override suspend fun holdButaca(numSala: Int, peliculaId: String, horario: String, req: HoldButacaRequest) : HttpStatusCode =
        httpClient.post("$baseUrl/$numSala/$peliculaId/$horario/butaca/hold"){
            contentType(ContentType.Application.Json)
            setBody(req)
        }.status

    /**
     * Libera una butaca de una sesión concreta al deseleccionarla
     */
    override suspend fun releaseButaca(numSala: Int, peliculaId: String, horario: String, req: HoldButacaRequest) : HttpStatusCode =
        httpClient.delete("$baseUrl/$numSala/$peliculaId/$horario/butaca/hold"){
            contentType(ContentType.Application.Json)
            setBody(req)
        }.status


    /**
     * Devuelve el estado de las butacas de una sesión
     */
    override suspend fun butacasStatus(numSala: Int, peliculaId: String, horario: String): ButacasStatusResponse =
        httpClient.get("$baseUrl/$numSala/$peliculaId/$horario/butacas/status").body()
}