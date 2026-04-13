package com.alejandro.proyecto_cines_frame.core.security
import com.alejandro.proyecto_cines_frame.core.security.SecureCredentialStore

expect object CredentialStoreFactory {
    fun create(): SecureCredentialStore
}