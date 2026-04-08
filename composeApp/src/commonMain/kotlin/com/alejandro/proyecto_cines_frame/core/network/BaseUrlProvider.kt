package com.alejandro.proyecto_cines_frame.core.network

interface BaseUrlProvider {
    val url: String
}

expect fun getUrl(): BaseUrlProvider