package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO
import com.alejandro.proyecto_cines_frame.domain.model.Baner

interface BanerRepository {
    suspend fun getBanersToday(): ApiResult<List<Baner>>
    suspend fun createBaner(baner: BanerDTO): ApiResult<Baner>
    suspend fun updateBaner(url: String, baner: BanerDTO): ApiResult<Baner>
    suspend fun deleteBaner(url: String): ApiResult<Baner>
}