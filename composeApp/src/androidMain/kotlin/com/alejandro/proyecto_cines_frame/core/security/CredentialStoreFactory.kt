package com.alejandro.proyecto_cines_frame.core.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AndroidSecureCredentialStore(
    context: Context
) : SecureCredentialStore {

    private val prefs = run {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "secure_credentials",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override suspend fun save(correo: String, password: String) = withContext(Dispatchers.IO) {
        prefs.edit()
            .putString("correo", correo)
            .putString("password", password)
            .apply()
    }

    override suspend fun read(): Credentials? = withContext(Dispatchers.IO) {
        val correo = prefs.getString("correo", null)
        val password = prefs.getString("password", null)
        if (correo.isNullOrBlank() || password.isNullOrBlank()) null else Credentials(correo, password)
    }

    override suspend fun clear() = withContext(Dispatchers.IO) {
        prefs.edit().clear().apply()
    }
}

object AppAndroidContextHolder {
    lateinit var appContext: android.content.Context
}

actual object CredentialStoreFactory {
    actual fun create(): SecureCredentialStore =
        AndroidSecureCredentialStore(AppAndroidContextHolder.appContext)
}