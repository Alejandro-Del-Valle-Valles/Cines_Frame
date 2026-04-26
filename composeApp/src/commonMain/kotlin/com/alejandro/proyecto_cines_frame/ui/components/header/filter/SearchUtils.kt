package com.alejandro.proyecto_cines_frame.ui.components.header.filter

import com.alejandro.proyecto_cines_frame.domain.model.Pelicula

//Utils Search
fun List<Pelicula>.applySearch(query: String): List<Pelicula> {
    val texto = query.trim()

    if (texto.length < 3) return this

    return filter {
        it.nombre.contains(texto, ignoreCase = true)
    }
}