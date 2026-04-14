package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.ProductoApi
import com.alejandro.proyecto_cines_frame.domain.repository.ProductoRepository

class ProductoRepositoryImpl(
    private val api: ProductoApi
) : ProductoRepository {
}