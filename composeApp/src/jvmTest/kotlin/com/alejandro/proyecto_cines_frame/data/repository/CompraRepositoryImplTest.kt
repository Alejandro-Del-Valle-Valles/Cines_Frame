package com.alejandro.proyecto_cines_frame.data.repository

import com.alejandro.proyecto_cines_frame.core.error.ApiResult
import com.alejandro.proyecto_cines_frame.data.remote.api.interfaces.CompraApi
import com.alejandro.proyecto_cines_frame.data.remote.dto.CompraDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraProductoCreateDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.LineaCompraProductoDTO
import com.alejandro.proyecto_cines_frame.data.remote.dto.ProductoDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest

class CompraRepositoryImplTest {

    private val api: CompraApi = mockk()
    private val repository = CompraRepositoryImpl(api)

    @Test
    fun createCompra_success_mapsResponseToDomainModel() = runTest {
        // Arrange
        val input = CompraDTO(
            correo = "cliente@correo.com",
            holdToken = "token-123",
            lineasCompra = listOf(
                LineaCompraProductoDTO(
                    numero = 1,
                    producto = ProductoDTO(
                        nombre = "Palomitas",
                        precio = 5.5f,
                        stock = 10,
                        alergenos = null
                    )
                )
            )
        )
        coEvery { api.createCompra(input) } returns input

        // Act
        val result = repository.createCompra(input)

        // Assert
        assertIs<ApiResult.Success<*>>(result)
        val compra = (result as ApiResult.Success<com.alejandro.proyecto_cines_frame.domain.model.Compra>).data
        assertEquals("cliente@correo.com", compra.usuario.correo)
        assertEquals(1, compra.lineasCompra.size)
        val linea = compra.lineasCompra.first()
        assertEquals(1, linea.numLinea)
        assertIs<com.alejandro.proyecto_cines_frame.domain.model.LineaCompraProducto>(linea)
        assertEquals("Palomitas", linea.producto.nombre)
        assertEquals(5.5f, linea.producto.precio)
        assertEquals(10, linea.producto.stock)
        coVerify(exactly = 1) { api.createCompra(input) }
    }

    @Test
    fun createCompra_success_whenBackendReturnsProductoCreateLine() = runTest {
        val input = CompraDTO(
            correo = "cliente@correo.com",
            holdToken = "token-123",
            lineasCompra = listOf(
                LineaCompraProductoCreateDTO(
                    numero = 1,
                    nombreProducto = "Refresco"
                )
            )
        )
        coEvery { api.createCompra(input) } returns input

        val result = repository.createCompra(input)

        assertIs<ApiResult.Success<*>>(result)
        val compra = (result as ApiResult.Success<com.alejandro.proyecto_cines_frame.domain.model.Compra>).data
        assertEquals("cliente@correo.com", compra.usuario.correo)
        assertTrue(compra.lineasCompra.isNotEmpty())
    }

    @Test
    fun createCompra_success_whenBackendReturnsPartialPayload() = runTest {
        val input = CompraDTO(
            correo = "cliente@correo.com",
            holdToken = "token-123",
            lineasCompra = emptyList()
        )
        coEvery { api.createCompra(input) } returns CompraDTO(correo = "cliente@correo.com")

        val result = repository.createCompra(input)

        assertIs<ApiResult.Success<*>>(result)
        val compra = (result as ApiResult.Success<com.alejandro.proyecto_cines_frame.domain.model.Compra>).data
        assertEquals("cliente@correo.com", compra.usuario.correo)
    }
}

