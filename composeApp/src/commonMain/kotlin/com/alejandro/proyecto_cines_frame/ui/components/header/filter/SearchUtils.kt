package com.alejandro.proyecto_cines_frame.ui.components.header.filter

import com.alejandro.proyecto_cines_frame.domain.model.Movie

//Utils Search
fun List<Movie>.applySearch(query: String): List<Movie> {
    if (query.length < 3) return this

    return filter {
        it.title.contains(query.trim(), ignoreCase = true)
    }
}
