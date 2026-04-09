package com.alejandro.proyecto_cines_frame.ui.components.header.filter

import com.alejandro.proyecto_cines_frame.domain.model.Pelicula

//Utils Search
fun List<Pelicula>.applySearch(query: String): List<Pelicula> {
    if (query.length < 3) return this

    return filter {
        it.nombre.contains(query.trim(), ignoreCase = true)
    }
}
