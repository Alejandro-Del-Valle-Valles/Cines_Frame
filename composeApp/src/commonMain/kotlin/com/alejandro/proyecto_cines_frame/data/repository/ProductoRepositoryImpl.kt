package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.toDomain
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.ProductoApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.ProductoDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.Producto
import com.alejandro.proyecto_cines_frame.domain.repository.ProductoRepository

class ProductoRepositoryImpl(
    private val api: ProductoApi
) : ProductoRepository {

    /**
     * Obtiene todos los productos
     */
    override suspend fun getAll(): ApiResult<List<Producto>> {
        return try {
            val dtos = api.getAll()
            ApiResult.Success(dtos.map { it.toDomain() })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Obtiene un producto por su nombre
     */
    override suspend fun getByNombre(nombre: String): ApiResult<Producto> {
        return try {
            val dto = api.getByNombre(nombre)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea un producto
     */
    override suspend fun createProducto(producto: ProductoDTO): ApiResult<Producto> {
        return try {
            val dto = api.createProducto(producto)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Actauliza el producto
     */
    override suspend fun updateProducto(
        nombre: String,
        producto: ProductoDTO
    ): ApiResult<Producto> {
        return try {
            val dto = api.updateProducto(nombre, producto)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Elimina el producto
     */
    override suspend fun deleteProducto(nombre: String): ApiResult<Producto> {
        return try {
            val dto = api.deleteProducto(nombre)
            ApiResult.Success(dto.toDomain())
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }
}