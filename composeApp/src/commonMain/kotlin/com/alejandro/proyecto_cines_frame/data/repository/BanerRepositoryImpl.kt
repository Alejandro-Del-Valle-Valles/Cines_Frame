package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.BanerAdapter
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.BanerApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.Baner
import com.alejandro.proyecto_cines_frame.domain.repository.BanerRepository

class BanerRepositoryImpl(
    val api: BanerApi
) : BanerRepository {

    /**
     * Devuelve todos los baners a mostrar hoy
     */
    override suspend fun getBanersToday(): ApiResult<List<Baner>> {
        return try {
            val dtos = api.getBanersToday()
            ApiResult.Success(dtos.map { BanerAdapter.toBaner(it) })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea un nuevo baner
     */
    override suspend fun createBaner(baner: BanerDTO): ApiResult<Baner> {
        return try {
            val dto = api.createBaner(baner)
            ApiResult.Success(BanerAdapter.toBaner(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Actualiza un baner
     */
    override suspend fun updateBaner(url: String,  baner: BanerDTO): ApiResult<Baner> {
        return try {
            val dto = api.updateBaner(url, baner)
            ApiResult.Success(BanerAdapter.toBaner(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Elimina un baner por la url de su imagen
     */
    override suspend fun deleteBaner(url: String): ApiResult<Baner> {
        return try {
            val dto = api.deleteBaner(url)
            ApiResult.Success(BanerAdapter.toBaner(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }
}