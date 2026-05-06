package com.alejandro.proyecto_cines_frame.data.remote.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

/**
 * Creates a JVM HttpClient using CIO that bypasses SSL validation.
 * Warning: Use only for local development.
 */
actual fun createPlatformHttpClient(): HttpClient {
    return HttpClient(CIO) {
        engine {
            https {
                trustManager = object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
                }
            }
        }
    }
}