package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.toDomain
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.CodigoDescuentoApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CodigoDescuentoCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento
import com.alejandro.proyecto_cines_frame.domain.repository.CodigoDescuentoRepository

class CodigoDescuentoRepositoryImpl(
    private val api: CodigoDescuentoApi
) : CodigoDescuentoRepository {

    /**
     * Devuelve todos los códigos de descuento.
     */
    override suspend fun getAll(): ApiResult<List<CodigoDescuento>> {
        return try {
            val dtos = api.getAll()
            ApiResult.Success(dtos.map { it.toDomain() })
        } catch (t: Throwable) {
            println(t.stackTraceToString())
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve un código de descuento por su ID.
     */
    override suspend fun getById(id: String): ApiResult<CodigoDescuento> {
        return try {
            val dto = api.getById(id)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            println(t.stackTraceToString())
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve un código de descuento por su código.
     */
    override suspend fun getByCodigo(id: String): ApiResult<CodigoDescuento> {
        return try {
            val dto = api.getByCodigo(id)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            println(t.stackTraceToString())
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea un nuevo código de descuento.
     */
    override suspend fun createCodigoDescuento(
        codigoDescuento: CodigoDescuentoCreateDTO
    ): ApiResult<CodigoDescuento> {
        return try {
            val dto = api.createCodigoDescuento(codigoDescuento)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            println(t.stackTraceToString())
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Actualiza un código de descuento.
     */
    override suspend fun updateCodigoDescuento(
        id: Int,
        codigoDescuento: CodigoDescuentoCreateDTO
    ): ApiResult<CodigoDescuento> {
        return try {
            val dto = api.updateCodigoDescuento(id, codigoDescuento)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            println(t.stackTraceToString())
            ApiResult.Error(t.toAppError())
        }
    }
}