package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.SalaDTO
import com.alejandro.proyecto_cines_frame.domain.model.Sala

interface SalaRepository {
    suspend fun getAll() : ApiResult<List<Sala>>
    suspend fun getByNumero(numero: Int) : ApiResult<Sala>
    suspend fun createSala(sala: SalaDTO) : ApiResult<Sala>
    suspend fun updateSala(sala: SalaDTO) : ApiResult<Sala>
    suspend fun deleteSala(numero: Int) : ApiResult<Sala>
}