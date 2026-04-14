package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.ParticipanteAdpater
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.ParticipanteApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.ParticipanteDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.Participante
import com.alejandro.proyecto_cines_frame.domain.repository.ParticipanteRepository

class ParticipanteRepositoryImpl(
    private val api: ParticipanteApi
) : ParticipanteRepository {

    /**
     * Obtiene todos los participantes.
     */
    override suspend fun getAll(): ApiResult<List<Participante>> {
        return try {
            val dtos = api.getAll()
            ApiResult.Success(dtos.map { ParticipanteAdpater.toParticipante(it)})
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Obtiene todos los partiicpantes por su nombre
     */
    override suspend fun getAllByNombre(nombre: String): ApiResult<List<Participante>> {
        return try {
            val dtos = api.getAllByNombre(nombre)
            ApiResult.Success(dtos.map { ParticipanteAdpater.toParticipante(it)})
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    override suspend fun getById(id: Int): ApiResult<ParticipanteDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun createParticipante(nombre: String): ApiResult<ParticipanteDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun updateParticipante(participante: ParticipanteDTO): ApiResult<ParticipanteDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteParticipante(id: Int): ApiResult<ParticipanteDTO> {
        TODO("Not yet implemented")
    }
}