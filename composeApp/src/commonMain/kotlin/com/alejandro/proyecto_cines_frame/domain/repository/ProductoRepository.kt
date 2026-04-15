package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.ProductoDTO
import com.alejandro.proyecto_cines_frame.domain.model.Producto

interface ProductoRepository {
    suspend fun getAll() : ApiResult<List<Producto>>
    suspend fun getByNombre(nombre: String) : ApiResult<Producto>
    suspend fun createProducto(producto: ProductoDTO) : ApiResult<Producto>
    suspend fun updateProducto(nombre: String, producto: ProductoDTO) : ApiResult<Producto>
    suspend fun deleteProducto(nombre: String) : ApiResult<Producto>
}