package com.alejandro.proyecto_cines_frame.core.network

class JvmBaseUrlProvider : BaseUrlProvider {
    override val url: String = "http://localhost:9090/api"
}

actual fun getUrl(): BaseUrlProvider = JvmBaseUrlProvider()