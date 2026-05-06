package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.toDomain
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.TipoEntradaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CreateTipoEntrada
import com.alejandro.proyecto_cines_frame.data.remote.dto.TipoEntradaDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.TipoEntrada
import com.alejandro.proyecto_cines_frame.domain.repository.TipoEntradaRepository

class TipoEntradaRepositoryImpl(
    private val api: TipoEntradaApi
) : TipoEntradaRepository {

    /**
     * Devuelve todos los tipos de entrada
     */
    override suspend fun getAll(): ApiResult<List<TipoEntrada>> {
        return try {
            val dtos = api.getAll()
            ApiResult.Success(dtos.map { it.toDomain() })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve un tipo de entrada por el id buscado
     */
    override suspend fun getById(id: Int): ApiResult<TipoEntrada> {
        return try {
            val dto = api.getById(id)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea un tipo de entrada nuevo
     */
    override suspend fun createTipoEntrada(tipoEntrada: CreateTipoEntrada): ApiResult<TipoEntrada> {
        return try {
            val dto = api.createTipoEntrada(tipoEntrada)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Actualiza un tipo de entrada
     */
    override suspend fun updateTipoEntrada(tipoEntrada: TipoEntradaDTO): ApiResult<TipoEntrada> {
        return try {
            val dto = api.updateTipoEntrada(tipoEntrada)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Elimina un tipo de entrada
     */
    override suspend fun deleteTipoEntrada(id: Int): ApiResult<TipoEntrada> {
        return try {
            val dto = api.deleteTipoEntrada(id)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }
}