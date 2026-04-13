package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.ProductoDTO

interface ProductoApi {
    suspend fun getAll() : List<ProductoDTO>
    suspend fun getByNombre(nombre: String) : ProductoDTO
    suspend fun createProducto(producto: ProductoDTO) : ProductoDTO
    suspend fun updateProducto(nombre: String, producto: ProductoDTO) : ProductoDTO
    suspend fun deleteProducto(nombre: String) : ProductoDTO
}