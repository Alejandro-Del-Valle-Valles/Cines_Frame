package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteDTO
import com.alejandro.proyecto_cines_frame.domain.model.Participante

interface ParticipanteRepository {
    suspend fun getAll() : ApiResult<List<Participante>>
    suspend fun getAllByNombre(nombre: String) : ApiResult<List<Participante>>
    suspend fun getById(id: Int) : ApiResult<ParticipanteDTO>
    suspend fun createParticipante(nombre: String) : ApiResult<ParticipanteDTO>
    suspend fun updateParticipante(participante: ParticipanteDTO) : ApiResult<ParticipanteDTO>
    suspend fun deleteParticipante(id: Int) : ApiResult<ParticipanteDTO>
}