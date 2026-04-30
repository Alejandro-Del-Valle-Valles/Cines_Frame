package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.CreateTipoEntrada
import com.alejandro.proyecto_cines_frame.data.remote.dto.TipoEntradaDTO
import com.alejandro.proyecto_cines_frame.domain.model.TipoEntrada

interface TipoEntradaRepository {
    suspend fun getAll() : ApiResult<List<TipoEntrada>>
    suspend fun getById(id: Int) : ApiResult<TipoEntrada>
    suspend fun createTipoEntrada(tipoEntrada: CreateTipoEntrada) : ApiResult<TipoEntrada>
    suspend fun updateTipoEntrada(tipoEntrada: TipoEntradaDTO) : ApiResult<TipoEntrada>
    suspend fun deleteTipoEntrada(id: Int) : ApiResult<TipoEntrada>
}