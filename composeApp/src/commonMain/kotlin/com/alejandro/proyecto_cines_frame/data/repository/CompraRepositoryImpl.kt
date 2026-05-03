package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.toDomain
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.CompraApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CompraDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.Compra
import com.alejandro.proyecto_cines_frame.domain.repository.CompraRepository

class CompraRepositoryImpl(
    private val api: CompraApi
) : CompraRepository {

    /**
     * Devuelve todas las compras del cliente
     */
    override suspend fun getAll(): ApiResult<List<Compra>> {
        return try {
            val dtos = api.getAll()
            ApiResult.Success(dtos.map { it.toDomain() })
        } catch (t: Throwable) {
            println(t.stackTraceToString())
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve todas las compras (Entradas y productos) de eventos futuros.
     */
    override suspend fun getFuturas(): ApiResult<List<Compra>> {
        return try {
            val dtos = api.getFuturas()
            ApiResult.Success(dtos.map { it.toDomain() })
        } catch (t: Throwable) {
            println(t.stackTraceToString())
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea una nueva compra y si no existe el usuario con el correo especificado lo crea.
     */
    override suspend fun createCompra(compra: CompraDTO): ApiResult<Compra> {
        return try {
            val dto = api.createCompra(compra)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            println(t.stackTraceToString())
            ApiResult.Error(t.toAppError())
        }
    }

}
