package com.alejandro.proyecto_cines_frame.core.security

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class DesktopSecureCredentialStore : SecureCredentialStore {

    private val file = File(System.getProperty("user.home"), ".cines_frame/secure_credentials.dat")

    // TODO mover este valor a config/build o un .env
    private val appSecret = "cines-frame-local-secret-v1"

    override suspend fun save(correo: String, password: String) = withContext(Dispatchers.IO) {
        ensureParent()

        val plain = "$correo\n$password".toByteArray(Charsets.UTF_8)
        val encrypted = encrypt(plain)
        file.writeText(Base64.getEncoder().encodeToString(encrypted))
    }

    override suspend fun read(): Credentials? = withContext(Dispatchers.IO) {
        if (!file.exists()) return@withContext null

        val b64 = file.readText().trim()
        if (b64.isBlank()) return@withContext null

        val encrypted = runCatching { Base64.getDecoder().decode(b64) }.getOrNull() ?: return@withContext null
        val plain = runCatching { decrypt(encrypted) }.getOrNull() ?: return@withContext null

        val parts = plain.toString(Charsets.UTF_8).split('\n', limit = 2)
        if (parts.size < 2) return@withContext null

        val correo = parts[0]
        val password = parts[1]
        if (correo.isBlank() || password.isBlank()) null else Credentials(correo, password)
    }

    override suspend fun clear() = withContext(Dispatchers.IO) {
        if (file.exists()) file.delete()
    }

    private fun ensureParent() {
        val parent = file.parentFile
        if (parent != null && !parent.exists()) parent.mkdirs()
    }

    private fun encrypt(plain: ByteArray): ByteArray {
        val key = deriveAesKey()
        val iv = ByteArray(12).also { SecureRandom().nextBytes(it) }

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(128, iv))
        val cipherText = cipher.doFinal(plain)

        // formato: [ivLength(4 bytes)] [iv] [cipherText]
        return ByteBuffer.allocate(4 + iv.size + cipherText.size)
            .putInt(iv.size)
            .put(iv)
            .put(cipherText)
            .array()
    }

    private fun decrypt(payload: ByteArray): ByteArray {
        val bb = ByteBuffer.wrap(payload)
        val ivLen = bb.int
        if (ivLen <= 0 || ivLen > 32) error("IV inválido")

        val iv = ByteArray(ivLen)
        bb.get(iv)

        val cipherText = ByteArray(bb.remaining())
        bb.get(cipherText)

        val key = deriveAesKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
        return cipher.doFinal(cipherText)
    }

    private fun deriveAesKey(): SecretKeySpec {
        // Derivación simple por SHA-256 (mínimo viable)
        // seed ligado a usuario+os+secreto app
        val seed = buildString {
            append(System.getProperty("user.name"))
            append("|")
            append(System.getProperty("os.name"))
            append("|")
            append(appSecret)
        }
        val digest = MessageDigest.getInstance("SHA-256").digest(seed.toByteArray(Charsets.UTF_8))
        return SecretKeySpec(digest.copyOf(32), "AES")
    }
}

actual object CredentialStoreFactory {
    actual fun create(): SecureCredentialStore = DesktopSecureCredentialStore()
}