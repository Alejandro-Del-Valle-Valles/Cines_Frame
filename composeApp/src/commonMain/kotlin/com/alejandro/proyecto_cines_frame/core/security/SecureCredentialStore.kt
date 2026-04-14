package com.alejandro.proyecto_cines_frame.core.security

interface SecureCredentialStore {
    suspend fun save(correo: String, password: String)
    suspend fun read(): Credentials?
    suspend fun clear()
}

data class Credentials(
    val correo: String,
    val password: String
)