package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.CompraDTO

interface CompraApi {
    suspend fun getAll() : List<CompraDTO>
    suspend fun getFuturas() : List<CompraDTO>
    suspend fun createCompra(compra: CompraDTO) : CompraDTO
    suspend fun getCompraPdf(id: String) : ByteArray
}