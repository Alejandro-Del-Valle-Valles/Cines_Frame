package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.adapter.PeliculaAdapter
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.PeliculaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.error.toAppError
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.repository.PeliculaRepository

class PeliculaRepositoryImpl(
    private val api: PeliculaApi
) : PeliculaRepository{

    /**
     * Devuelve todas las películas sin los participantes
     */
    override suspend fun getAllBasic(): ApiResult<List<Pelicula>> {
        return try {
            val dtos = api.getAllBasic() // si status 4xx/5xx => excepción
            ApiResult.Success(dtos.map { PeliculaAdapter.toPelicula(it)})
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve todas las películas con los participantes
     */
    override suspend fun getAllCompleto(): ApiResult<List<Pelicula>> {
        return try {
            val dtos = api.getAllCompleto()
            ApiResult.Success(dtos.map { PeliculaAdapter.toPelicula(it) })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve todas las películas sin los participantes por el nombre
     */
    override suspend fun getAllBasicByNombre(nombre: String): ApiResult<List<Pelicula>> {
        return try {
            val dtos = api.getAllBasicByNombre(nombre)
            ApiResult.Success(dtos.map { PeliculaAdapter.toPelicula(it) })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve todas las películas con los partiicpantes por el nombre
     */
    override suspend fun getAllCompletoByNombre(nombre: String): ApiResult<List<Pelicula>> {
        return try {
            val dtos = api.getAllCompletoByNombre(nombre)
            ApiResult.Success(dtos.map { PeliculaAdapter.toPelicula(it) })
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Devuelve una película con sus participantes por su nombre
     */
    override suspend fun getById(id: String): ApiResult<Pelicula> {
        return try {
            val dto = api.getById(id)
            ApiResult.Success(PeliculaAdapter.toPelicula(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Crea una nueva película
     */
    override suspend fun createPelicula(pelicula: PeliculaCreateDTO): ApiResult<Pelicula> {
        return try {
            val dto = api.createPelicula(pelicula)
            ApiResult.Success(PeliculaAdapter.toPelicula(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Actualiza la película
     */
    override suspend fun updatePelicula(
        id: String,
        pelicula: PeliculaCreateDTO
    ): ApiResult<Pelicula> {
        return try {
            val dto = api.updatePelicula(id, pelicula)
            ApiResult.Success(PeliculaAdapter.toPelicula(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }

    /**
     * Elimina la película
     */
    override suspend fun deletePelicula(id: String): ApiResult<Pelicula> {
        return try {
            val dto = api.deletePelicula(id)
            ApiResult.Success(PeliculaAdapter.toPelicula(dto))
        } catch (t: Throwable) {
            ApiResult.Error(t.toAppError())
        }
    }
}