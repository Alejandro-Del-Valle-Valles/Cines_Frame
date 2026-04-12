package com.alejandro.proyecto_cines_frame.domain.repository

import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCreateDTO
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula


interface PeliculaRepository {
    suspend fun getAllBasic(): List<Pelicula>
    suspend fun getAllCompleto(): List<Pelicula>
    suspend fun getAllBasicByNombre(nombre: String): List<Pelicula>
    suspend fun getAllCompletoByNombre(nombre: String): List<Pelicula>
    suspend fun getById(id: String): Pelicula
    suspend fun createPelicula(pelicula: PeliculaCreateDTO): Pelicula
    suspend fun updatePelicula(id: String, pelicula: PeliculaCreateDTO): Pelicula
    suspend fun deletePelicula(id: String): Pelicula
}