package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.data.adapter.PeliculaAdapter
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.PeliculaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCreateDTO
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.repository.PeliculaRepository

class PeliculaRepositoryImpl(
    private val api: PeliculaApi
) : PeliculaRepository{

    override suspend fun getAllBasic(): List<Pelicula> =
        api.getAllBasic().map { PeliculaAdapter.toPelicula(it) }

    override suspend fun getAllCompleto(): List<Pelicula> =
        api.getAllCompleto().map { PeliculaAdapter.toPelicula(it) }

    override suspend fun getAllBasicByNombre(nombre: String): List<Pelicula> =
        api.getAllBasicByNombre(nombre).map { PeliculaAdapter.toPelicula(it) }

    override suspend fun getAllCompletoByNombre(nombre: String): List<Pelicula> =
        api.getAllCompletoByNombre(nombre).map { PeliculaAdapter.toPelicula(it) }

    override suspend fun getById(id: String): Pelicula =
        PeliculaAdapter.toPelicula(api.getById(id))

    override suspend fun createPelicula(pelicula: PeliculaCreateDTO): Pelicula =
        PeliculaAdapter.toPelicula(api.createPelicula(pelicula))

    override suspend fun updatePelicula(
        id: String,
        pelicula: PeliculaCreateDTO
    ): Pelicula =
        PeliculaAdapter.toPelicula(api.updatePelicula(id, pelicula))

    override suspend fun deletePelicula(id: String): Pelicula =
        PeliculaAdapter.toPelicula(api.deletePelicula(id))
}