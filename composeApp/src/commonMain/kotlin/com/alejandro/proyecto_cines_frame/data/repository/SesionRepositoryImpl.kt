package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.SesionAdapter
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.SesionApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.SesionCrudDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.domain.repository.SesionRepository

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
}