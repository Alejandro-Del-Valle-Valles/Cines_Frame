package com.alejandro.proyecto_cines_frame.domain.enums

/**
 * Estados de una película. En la BBDD se guarda como booleano
 */
enum class PeliculaEstado {
    CARTELERA, //True
    ESTRENO, //False
    INACTIVA //Null
}