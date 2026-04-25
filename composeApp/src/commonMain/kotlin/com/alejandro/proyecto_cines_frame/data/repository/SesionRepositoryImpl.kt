package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.core.error.AppError
import com.alejandro.proyecto_cines_frame.data.adapter.HoldTokenAdapter
import com.alejandro.proyecto_cines_frame.data.adapter.SesionAdapter
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.SesionApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.ButacasStatusResponse
import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldButacaRequest
import com.alejandro.proyecto_cines_frame.data.remote.dto.HoldTokenResponse
import com.alejandro.proyecto_cines_frame.domain.model.HoldToken
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCrudDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.domain.repository.SesionRepository
import io.ktor.http.HttpStatusCode

class SesionRepositoryImpl(
    private val api: SesionApi
) : SesionRepository {

    /**
     * Obtiene todas las sesiones
     */
    override suspend fun getAll(): ApiResult<List<Sesion>> {
        return try {
            val dtos = api.getAll()
            ApiResult.Success(dtos.map { SesionAdapter.toSesion(it) })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Obtiene las sesiones de un rango horario (Día y hora)
     */
    override suspend fun getByRangoHorario(
        inicio: String,
        fin: String
    ): ApiResult<List<Sesion>> {
        return try {
            val dtos = api.getByRangoHorario(inicio, fin)
            ApiResult.Success(dtos.map { SesionAdapter.toSesion(it) })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Obtiene una sesión en concreto
     */
    override suspend fun getSesion(
        numSala: Int,
        peliculaId: String,
        horario: String
    ): ApiResult<Sesion> {
        return try {
            val dto = api.getSesion(numSala, peliculaId, horario)
            ApiResult.Success(SesionAdapter.toSesion(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea una sesión
     */
    override suspend fun createSesion(sesion: SesionCrudDTO): ApiResult<Sesion> {
        return try {
            val dto = api.createSesion(sesion)
            ApiResult.Success(SesionAdapter.toSesion(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Elimina una sesión
     */
    override suspend fun deleteSesion(sesion: SesionCrudDTO): ApiResult<Sesion> {
        return try {
            val dto = api.deleteSesion(sesion)
            ApiResult.Success(SesionAdapter.toSesion(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea un token para poder reservar butacas de una sesión
     */
    override suspend fun createHoldToken(numSala: Int, peliculaId: String, horario: String): ApiResult<HoldToken> {
        return try {
            val holdTokenResponse = api.createHoldToken(numSala, peliculaId, horario)
            ApiResult.Success(HoldTokenAdapter.toHoldToken(holdTokenResponse))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Reserva una butaca de una sesión
     */
    override suspend fun holdButaca(numSala: Int, peliculaId: String, horario: String, req: HoldButacaRequest): ApiResult<Unit> {
        return try {
            val response = api.holdButaca(numSala, peliculaId, horario, req)
            when (response) {
                HttpStatusCode.OK,
                HttpStatusCode.Created,
                HttpStatusCode.NoContent -> ApiResult.Success(Unit)

                HttpStatusCode.BadRequest ->
                    ApiResult.Error(AppError.Validation(mapOf("request" to "Datos de bloqueo inválidos")))
                HttpStatusCode.Unauthorized ->
                    ApiResult.Error(AppError.Unauthorized(mapOf("auth" to "No autenticado")))
                HttpStatusCode.Forbidden ->
                    ApiResult.Error(AppError.Forbidden(mapOf("auth" to "No autorizado")))
                HttpStatusCode.NotFound ->
                    ApiResult.Error(AppError.NotFound(mapOf("sesion" to "Sesión no encontrada")))
                HttpStatusCode.Conflict ->
                    ApiResult.Error(AppError.Conflict(mapOf("butaca" to "Butaca ocupada o bloqueada")))
                else ->
                    ApiResult.Error(
                        AppError.Server(
                            code = response.value,
                            details = mapOf("error" to "Error HTTP ${response.value}")
                        )
                    )
            }
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Libera una butaca de una sesión
     */
    override suspend fun releaseButaca(numSala: Int, peliculaId: String, horario: String, req: HoldButacaRequest): ApiResult<Unit> {
        return try {
            val response = api.releaseButaca(numSala, peliculaId, horario, req)
            when (response) {
                HttpStatusCode.OK,
                HttpStatusCode.Created,
                HttpStatusCode.NoContent -> ApiResult.Success(Unit)

                HttpStatusCode.BadRequest ->
                    ApiResult.Error(AppError.Validation(mapOf("request" to "Datos de bloqueo inválidos")))
                HttpStatusCode.Unauthorized ->
                    ApiResult.Error(AppError.Unauthorized(mapOf("auth" to "No autenticado")))
                HttpStatusCode.Forbidden ->
                    ApiResult.Error(AppError.Forbidden(mapOf("auth" to "No autorizado")))
                HttpStatusCode.NotFound ->
                    ApiResult.Error(AppError.NotFound(mapOf("sesion" to "Sesión no encontrada")))
                HttpStatusCode.Conflict ->
                    ApiResult.Error(AppError.Conflict(mapOf("butaca" to "Butaca ocupada o bloqueada")))
                else ->
                    ApiResult.Error(
                        AppError.Server(
                            code = response.value,
                            details = mapOf("error" to "Error HTTP ${response.value}")
                        )
                    )
            }
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve el estado de las butacas de una sesión
     */
    override suspend fun butacasStatus(numSala: Int, peliculaId: String, horario: String): ApiResult<ButacasStatusResponse> {
        return try {
            val holdTokenResponse = api.butacasStatus(numSala, peliculaId, horario)
            ApiResult.Success(holdTokenResponse)
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }
}