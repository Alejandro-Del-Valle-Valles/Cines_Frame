package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.BanerDTO

interface BanerApi {
    suspend fun getBanersToday(): List<BanerDTO>
    suspend fun getAll(): List<BanerDTO>
    suspend fun createBaner(baner: BanerDTO): BanerDTO
    suspend fun updateBaner(baner: BanerDTO): BanerDTO
    suspend fun deleteBaner(id: Int): BanerDTO
}