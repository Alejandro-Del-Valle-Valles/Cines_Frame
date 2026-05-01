package com.alejandro.proyecto_cines_frame.data.remote.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@JsonClassDiscriminator("type")
@Serializable
sealed class LineaCompraDTO {
    abstract val numero: Int
}

@Serializable
@SerialName("ENTRADA")
data class LineaCompraEntradaDTO(
    override val numero: Int,
    val entrada: EntradaDTO
) : LineaCompraDTO()

@Serializable
@SerialName("PRODUCTO_CREATE")
data class LineaCompraProductoCreateDTO(
    override val numero: Int,
    val nombreProducto: String
) : LineaCompraDTO()

@Serializable
@SerialName("PRODUCTO_INFO")
data class LineaCompraProductoDTO(
    override val numero: Int,
    val producto: ProductoDTO
) : LineaCompraDTO()