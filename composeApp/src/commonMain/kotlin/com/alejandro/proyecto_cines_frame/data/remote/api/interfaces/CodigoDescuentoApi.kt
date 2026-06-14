package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.CodigoDescuentoCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.CodigoDescuentoDTO
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento

interface CodigoDescuentoApi {
    suspend fun getAll(): List<CodigoDescuentoDTO>
    suspend fun getById(id: String): CodigoDescuentoDTO
    suspend fun getByCodigo(id: String): CodigoDescuentoDTO
    suspend fun createCodigoDescuento(codigoDescuento: CodigoDescuentoCreateDTO): CodigoDescuentoDTO
    suspend fun updateCodigoDescuento(id: Int, codigoDescuento: CodigoDescuentoCreateDTO): CodigoDescuentoDTO
}