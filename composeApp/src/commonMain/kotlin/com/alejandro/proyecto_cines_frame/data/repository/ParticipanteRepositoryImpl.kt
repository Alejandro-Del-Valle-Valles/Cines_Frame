package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.toDomain
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
            ApiResult.Success(dtos.map { it.toDomain() })
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
            ApiResult.Success(dtos.map { it.toDomain() })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Obtiene un participante por su ID
     */
    override suspend fun getById(id: Int): ApiResult<Participante> {
        return try {
            val dto = api.getById(id)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea un nuevo participante
     */
    override suspend fun createParticipante(nombre: String): ApiResult<Participante> {
        return try {
            val dto = api.createParticipante(nombre)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Actualiza los datos del participante
     */
    override suspend fun updateParticipante(participante: ParticipanteDTO): ApiResult<Participante> {
        return try {
            val dto = api.updateParticipante(participante)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Elimina el participante
     */
    override suspend fun deleteParticipante(id: Int): ApiResult<Participante> {
        return try {
            val dto = api.deleteParticipante(id)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }
}