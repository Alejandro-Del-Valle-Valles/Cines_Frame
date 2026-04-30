package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.toDomain
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.AlergenoApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.AlergenoDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.Alergeno
import com.alejandro.proyecto_cines_frame.domain.repository.AlergenoRepository

class AlergenoRepositoryImpl(
    private val api: AlergenoApi
) : AlergenoRepository {

    /**
     * Devuelve todos los alérgenos
     */
    override suspend fun getAll(): ApiResult<List<Alergeno>> {
        return try {
            val dtos = api.getAll()
            ApiResult.Success(dtos.map { it.toDomain() })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve un alérgeno por su nombre
     */
    override suspend fun getByNombre(nombre: String): ApiResult<Alergeno> {
        return try {
            val dto = api.getByNombre(nombre)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea un uevo alérgeno
     */
    override suspend fun createAlergeno(alergeno: AlergenoDTO): ApiResult<Alergeno> {
        return try {
            val dto = api.createAlergeno(alergeno)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Actualiza el alérgeno
     */
    override suspend fun updateAlergeno(
        nombre: String,
        alergeno: AlergenoDTO
    ): ApiResult<Alergeno> {
        return try {
            val dto = api.updateAlergeno(nombre, alergeno)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Elimina el alérgeno
     */
    override suspend fun deleteAlergeno(nombre: String): ApiResult<Alergeno> {
        return try {
            val dto = api.deleteAlergeno(nombre)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }
}