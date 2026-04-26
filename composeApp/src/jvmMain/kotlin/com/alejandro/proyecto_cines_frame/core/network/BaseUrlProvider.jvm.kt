package com.alejandro.proyecto_cines_frame.core.network

class JvmBaseUrlProvider : BaseUrlProvider {
    //override val url: String = "http://localhost:9090/api"
    override val url: String = "http://192.168.1.13:9090/api"
}

actual fun getUrl(): BaseUrlProvider = JvmBaseUrlProvider()