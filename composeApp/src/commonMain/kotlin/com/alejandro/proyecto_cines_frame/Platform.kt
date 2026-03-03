package com.alejandro.proyecto_cines_frame

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform