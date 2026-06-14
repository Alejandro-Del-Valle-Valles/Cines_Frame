package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.CodigoDescuentoCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCreateDTO
import com.alejandro.proyecto_cines_frame.domain.model.CodigoDescuento
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula

interface CodigoDescuentoRepository {
    suspend fun getAll(): ApiResult<List<CodigoDescuento>>
    suspend fun getById(id: String): ApiResult<CodigoDescuento>
    suspend fun getByCodigo(id: String): ApiResult<CodigoDescuento>
    suspend fun createCodigoDescuento(codigoDescuento: CodigoDescuentoCreateDTO): ApiResult<CodigoDescuento>
    suspend fun updateCodigoDescuento(id: Int, codigoDescuento: CodigoDescuentoCreateDTO): ApiResult<CodigoDescuento>
}