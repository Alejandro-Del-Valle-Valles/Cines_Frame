package com.alejandro.proyecto_cines_frame.data.remote.api.interfaces

import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCompletoDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaDTO

interface PeliculaApi {
    suspend fun getAllBasic(): List<PeliculaDTO>
    suspend fun getAllCompleto(): List<PeliculaCompletoDTO>
    suspend fun getAllBasicByNombre(nombre: String): List<PeliculaDTO>
    suspend fun getAllCompletoByNombre(nombre: String): List<PeliculaCompletoDTO>
    suspend fun getById(id: String): PeliculaCompletoDTO
    suspend fun createPelicula(pelicula: PeliculaCreateDTO): PeliculaCompletoDTO
    suspend fun updatePelicula(id: String, pelicula: PeliculaCreateDTO): PeliculaCompletoDTO
    suspend fun deletePelicula(id: String): PeliculaDTO
}