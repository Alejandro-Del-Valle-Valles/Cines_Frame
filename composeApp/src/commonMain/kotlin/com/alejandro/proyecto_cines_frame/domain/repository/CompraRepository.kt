package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.CompraDTO
import com.alejandro.proyecto_cines_frame.domain.model.Compra

interface CompraRepository {
    suspend fun getAll() : ApiResult<List<Compra>>
    suspend fun getFuturas() : ApiResult<List<Compra>>
    suspend fun createCompra(compra: CompraDTO) : ApiResult<Compra>
    suspend fun getCompraPdf(id: String): ApiResult<ByteArray>
}