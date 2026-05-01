package com.alejandro.proyecto_cines_frame.core.network

class AndroidBaseUrlProvider : BaseUrlProvider {
    //override val url: String = "https://10.0.2.2:8443/api"
    override val url: String = "https://192.168.1.13:8443/api"
}

actual fun getUrl(): BaseUrlProvider = AndroidBaseUrlProvider()