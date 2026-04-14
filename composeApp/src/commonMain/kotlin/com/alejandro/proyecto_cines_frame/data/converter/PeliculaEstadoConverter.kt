package com.alejandro.proyecto_cines_frame.data.converter

import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado

/**
 * Convertidor a estado de Película
 */
object PeliculaEstadoConverter {

    /**
     * Trasnforma un valor boolean a un estado de película
     * null = Inactiva, true = Cartelera, false = Etsreno
     */
    fun toPeliculaEstado(estado: Boolean?) =
        if(estado == null) PeliculaEstado.INACTIVA
        else if(estado) PeliculaEstado.CARTELERA
        else PeliculaEstado.ESTRENO

    /**
     * Trasnforma un estado de PeliculaEstado en booleano
     * null si es incativa, true si es Cartelera, false si es otro
     */
    fun toBooleanEstado(estado: PeliculaEstado) =
        when (estado) {
            PeliculaEstado.INACTIVA -> null
            PeliculaEstado.CARTELERA -> true
            else -> false
        }

}