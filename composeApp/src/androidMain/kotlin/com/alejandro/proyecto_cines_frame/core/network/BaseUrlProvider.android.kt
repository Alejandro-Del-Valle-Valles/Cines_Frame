package com.alejandro.proyecto_cines_frame.core.network

class AndroidBaseUrlProvider : BaseUrlProvider {
    override val url: String = "http://10.0.2.2:9090/api"
}

actual fun getUrl(): BaseUrlProvider = AndroidBaseUrlProvider()