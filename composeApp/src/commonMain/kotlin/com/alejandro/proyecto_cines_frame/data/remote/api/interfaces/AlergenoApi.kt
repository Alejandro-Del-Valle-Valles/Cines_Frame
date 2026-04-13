package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.AlergenoDTO

interface AlergenoApi {
    suspend fun getAll() : List<AlergenoDTO>
    suspend fun getByNombre(nombre: String) : AlergenoDTO
    suspend fun createAlergeno(alergeno: AlergenoDTO) : AlergenoDTO
    suspend fun updateAlergeno(nombre: String, alergeno: AlergenoDTO) : AlergenoDTO
    suspend fun deleteAlergeno(nombre: String) : AlergenoDTO
}