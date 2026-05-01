package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.CreateTipoEntrada
import com.alejandro.proyecto_cines_frame.data.remote.dto.TipoEntradaDTO

interface TipoEntradaApi {
    suspend fun getAll() : List<TipoEntradaDTO>
    suspend fun getById(id: Int) : TipoEntradaDTO
    suspend fun createTipoEntrada(tipoEntrada: CreateTipoEntrada) : TipoEntradaDTO
    suspend fun updateTipoEntrada(tipoEntrada: TipoEntradaDTO) : TipoEntradaDTO
    suspend fun deleteTipoEntrada(id: Int) : TipoEntradaDTO
}