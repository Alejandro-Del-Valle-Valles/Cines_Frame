package com.alejandro.proyecto_cines_frame.data.remote.client

import com.alejandro.proyecto_cines_frame.core.session.TokenStore
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer

/**
 * Provides a platform-specific HttpClient instance.
 */
expect fun createPlatformHttpClient(): HttpClient

object HttpClientFactory {

    /**
     * Creates an HttpClient with shared configuration applied
     * on top of the platform-specific engine.
     */
    fun create(): HttpClient =
        createPlatformHttpClient().config {
            expectSuccess = false

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        explicitNulls = false
                    }
                )
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        TokenStore.accessToken?.let { BearerTokens(it, "") }
                    }
                }
            }
        }
}