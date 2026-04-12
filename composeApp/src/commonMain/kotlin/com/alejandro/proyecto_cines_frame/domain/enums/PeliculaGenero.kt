package com.alejandro.proyecto_cines_frame.domain.enums

import kotlinx.serialization.Serializable

/**
 * Géneros de las películas y su nombre en String
 */
@Serializable
enum class PeliculaGenero(val label: String) {
    ACCION("Acción"),
    TERROR("Terror"),
    CIENCIA_FICCION("Ciencia Ficción"),
    COMEDIA("Comedia"),
    ROMANTICA("Romántica"),
    CINE_NEGRO("Cine Negro)"),
    DRAMA("Drama)"),
    HISTORICA("Histórica"),
    BELICA("Bélica"),
    POLICIACA("Peliciaca"),
    DOCUMENTAL("Documental"),
    MUSICAL("Musical"),
    INFANTIL("Infantil"),
    SUSPENSE("Suspense"),
    WESTERN_CLASICO("Western Clásico"),
    MAGICO("Mágico"),
    AVENTURA("Aventura"),
    FANTASIA("Fantasía")
}