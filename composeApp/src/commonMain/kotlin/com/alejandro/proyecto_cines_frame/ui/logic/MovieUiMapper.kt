package com.alejandro.proyecto_cines_frame.ui.logic

import com.alejandro.proyecto_cines_frame.domain.enums.PeliculaEstado
import com.alejandro.proyecto_cines_frame.domain.model.Pelicula
import com.alejandro.proyecto_cines_frame.domain.model.Sesion
import com.alejandro.proyecto_cines_frame.ui.components.filter.FilterState

object MovieUiMapper {

    //CARTELERA FILTRADA
    fun getFilteredCartelera(
        sessions: List<Sesion>,
        filter: FilterState
    ): Pair<List<Pelicula>, List<Sesion>> {

        val filteredSessions = sessions
            .filter { it.pelicula.estado == PeliculaEstado.CARTELERA }
            .filter { s ->

                val matchDay = filter.selectedDay?.let {
                    s.horario.date == it.date
                } ?: true

                val match3D = filter.is3D?.let {
                    s.tresD == it
                } ?: true

                val matchVOSE = if (filter.isVOSE) s.vose else true

                matchDay && match3D && matchVOSE
            }

        val movies = filteredSessions
            .map { it.pelicula }
            .distinctBy { it.id }

        return movies to filteredSessions
    }

    //ESTRENOS (sin filtro por ahora)
    fun getEstrenos(
        sessions: List<Sesion>
    ): Pair<List<Pelicula>, List<Sesion>> {

        val estrenoSessions = sessions.filter {
            it.pelicula.estado == PeliculaEstado.ESTRENO
        }

        val movies = estrenoSessions
            .map { it.pelicula }
            .distinctBy { it.id }

        return movies to estrenoSessions
    }
}
