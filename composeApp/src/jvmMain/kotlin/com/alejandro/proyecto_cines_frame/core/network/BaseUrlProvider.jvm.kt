package com.alejandro.proyecto_cines_frame.core.network

class JvmBaseUrlProvider : BaseUrlProvider {
    //override val url: String = "http://localhost:8443/api"
    //override val url: String = "http://192.168.1.13:8443/api"
    override val url: String = "https://api-tfg-dm7g.onrender.com/api"
}

actual fun getUrl(): BaseUrlProvider = JvmBaseUrlProvider()