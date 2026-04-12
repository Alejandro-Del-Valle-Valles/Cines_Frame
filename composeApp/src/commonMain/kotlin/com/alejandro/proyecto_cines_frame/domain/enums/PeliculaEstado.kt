package com.alejandro.proyecto_cines_frame.domain.enums

import kotlinx.serialization.Serializable

/**
 * Estados de una película. En la BBDD se guarda como booleano
 */

@Serializable
enum class PeliculaEstado {
    CARTELERA, //True
    ESTRENO, //False
    INACTIVA //Null
}