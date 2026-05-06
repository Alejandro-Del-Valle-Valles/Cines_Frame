package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCreateDTO
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula


interface PeliculaRepository {
    suspend fun getAllBasic(): ApiResult<List<Pelicula>>
    suspend fun getAllCompleto(): ApiResult<List<Pelicula>>
    suspend fun getAllBasicByNombre(nombre: String): ApiResult<List<Pelicula>>
    suspend fun getAllCompletoByNombre(nombre: String): ApiResult<List<Pelicula>>
    suspend fun getById(id: String): ApiResult<Pelicula>
    suspend fun getByIdWithSesiones(id: String): ApiResult<Pelicula>
    suspend fun createPelicula(pelicula: PeliculaCreateDTO): ApiResult<Pelicula>
    suspend fun updatePelicula(id: String, pelicula: PeliculaCreateDTO): ApiResult<Pelicula>
    suspend fun deletePelicula(id: String): ApiResult<Pelicula>
}