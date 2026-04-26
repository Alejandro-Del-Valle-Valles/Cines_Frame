package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.SalaAdapter
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.SalaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.SalaDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.Sala
import com.alejandro.proyecto_cines_frame.domain.repository.SalaRepository

class SalaRepositoryImpl(
    private val api: SalaApi
) : SalaRepository {

    /**
     * Devuelve todas las salas
     */
    override suspend fun getAll(): ApiResult<List<Sala>> {
        return try {
            val dtos = api.getAll()
            ApiResult.Success(dtos.map { SalaAdapter.toSala(it) })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve una sala por su número
     */
    override suspend fun getByNumero(numero: Int): ApiResult<Sala> {
        return try {
            val dto = api.getByNumero(numero)
            ApiResult.Success(SalaAdapter.toSala(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea una sala
     */
    override suspend fun createSala(sala: SalaDTO): ApiResult<Sala> {
        return try {
            val dto = api.createSala(sala)
            ApiResult.Success(SalaAdapter.toSala(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Actualiza la sala
     */
    override suspend fun updateSala(sala: SalaDTO): ApiResult<Sala> {
        return try {
            val dto = api.updateSala(sala)
            ApiResult.Success(SalaAdapter.toSala(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Elimina la sala
     */
    override suspend fun deleteSala(numero: Int): ApiResult<Sala> {
        return try {
            val dto = api.deleteSala(numero)
            ApiResult.Success(SalaAdapter.toSala(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }
}