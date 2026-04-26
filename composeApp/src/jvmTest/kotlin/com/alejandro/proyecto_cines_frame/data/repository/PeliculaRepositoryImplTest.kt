package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.PeliculaApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCompletoDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.PeliculaCreateDTO
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaGenero
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalTime

class PeliculaRepositoryImplTest {

    private val api: PeliculaApi = mockk()
    private val repository = PeliculaRepositoryImpl(api)

    @Test
    fun createPelicula_success_mapsResponseToDomainModel() = runTest {
        // Arrange
        val input = PeliculaCreateDTO(
            nombre = "Interestelar",
            descripcion = "Viaje espacial",
            genero = PeliculaGenero.CIENCIA_FICCION,
            url = "https://img/pelicula.jpg",
            duracion = "02:49:00",
            edad = 12,
            enCartelera = true
        )
        val response = PeliculaCompletoDTO(
            id = "pel-1",
            nombre = input.nombre,
            descripcion = input.descripcion,
            genero = input.genero,
            url = input.url,
            duracion = input.duracion,
            edad = input.edad,
            enCartelera = input.enCartelera,
            participantes = emptyList()
        )
        coEvery { api.createPelicula(input) } returns response

        // Act
        val result = repository.createPelicula(input)

        // Assert
        assertIs<ApiResult.Success<*>>(result)
        val pelicula = (result as ApiResult.Success<*>).data as Pelicula
        assertEquals("pel-1", peliculaField(pelicula, "id"))
        assertEquals("Interestelar", peliculaField(pelicula, "nombre"))
        assertEquals("Viaje espacial", peliculaField(pelicula, "descripcion"))
        assertEquals(PeliculaEstado.CARTELERA, peliculaField(pelicula, "estado"))
        assertEquals("https://img/pelicula.jpg", peliculaField(pelicula, "portada"))
        assertEquals(LocalTime.parse("02:49:00"), peliculaField(pelicula, "duracion"))
        assertEquals(12, peliculaField(pelicula, "calificacionEdad"))
        assertEquals(PeliculaGenero.CIENCIA_FICCION, peliculaField(pelicula, "genero"))
        assertTrue((peliculaField(pelicula, "creditos") as List<*>).isEmpty())
        coVerify(exactly = 1) { api.createPelicula(input) }
    }

    @Test
    fun updatePelicula_success_mapsResponseToDomainModel() = runTest {
        // Arrange
        val input = PeliculaCreateDTO(
            nombre = "Interestelar 2",
            descripcion = "Secuela",
            genero = PeliculaGenero.AVENTURA,
            url = null,
            duracion = "01:55:00",
            edad = 16,
            enCartelera = false
        )
        val response = PeliculaCompletoDTO(
            id = "pel-2",
            nombre = input.nombre,
            descripcion = input.descripcion,
            genero = input.genero,
            url = input.url,
            duracion = input.duracion,
            edad = input.edad,
            enCartelera = input.enCartelera,
            participantes = emptyList()
        )
        coEvery { api.updatePelicula("pel-2", input) } returns response

        // Act
        val result = repository.updatePelicula("pel-2", input)

        // Assert
        assertIs<ApiResult.Success<*>>(result)
        val pelicula = (result as ApiResult.Success<*>).data as Pelicula
        assertEquals("pel-2", peliculaField(pelicula, "id"))
        assertEquals("Interestelar 2", peliculaField(pelicula, "nombre"))
        assertEquals("Secuela", peliculaField(pelicula, "descripcion"))
        assertEquals(PeliculaEstado.ESTRENO, peliculaField(pelicula, "estado"))
        assertEquals(null, peliculaField(pelicula, "portada"))
        assertEquals(LocalTime.parse("01:55:00"), peliculaField(pelicula, "duracion"))
        assertEquals(16, peliculaField(pelicula, "calificacionEdad"))
        assertEquals(PeliculaGenero.AVENTURA, peliculaField(pelicula, "genero"))
        assertTrue((peliculaField(pelicula, "creditos") as List<*>).isEmpty())
        coVerify(exactly = 1) { api.updatePelicula("pel-2", input) }
    }

    @Test
    fun updatePelicula_failure_returnsError() = runTest {
        // Arrange
        val input = PeliculaCreateDTO(
            nombre = "Fallida",
            descripcion = null,
            genero = PeliculaGenero.DOCUMENTAL,
            url = null,
            duracion = "00:50:00",
            edad = null,
            enCartelera = null
        )
        coEvery { api.updatePelicula("pel-error", input) } throws RuntimeException("error")

        // Act
        val result = repository.updatePelicula("pel-error", input)

        // Assert
        assertIs<ApiResult.Error>(result)
        coVerify(exactly = 1) { api.updatePelicula("pel-error", input) }
    }

    private fun peliculaField(pelicula: Pelicula, fieldName: String): Any? {
        val field = pelicula.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(pelicula)
    }
}

