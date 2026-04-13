package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.AlergenoDTO
import com.alejandro.proyecto_cines_frame.domain.model.Alergeno


interface AlergenoRepository {
    suspend fun getAll() : ApiResult<List<Alergeno>>
    suspend fun getByNombre(nombre: String) : ApiResult<Alergeno>
    suspend fun createAlergeno(alergeno: AlergenoDTO) : ApiResult<Alergeno>
    suspend fun updateAlergeno(nombre: String, alergeno: AlergenoDTO) : ApiResult<Alergeno>
    suspend fun deleteAlergeno(nombre: String) : ApiResult<Alergeno>
}