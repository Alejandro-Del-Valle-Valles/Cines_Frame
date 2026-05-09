package com.alejandro.proyecto_cines_frame.core.download

import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader

actual fun getDownloadsDirectory(): String {
    val userHome = System.getProperty("user.home")
    val osName = System.getProperty("os.name").lowercase()

    // 1. On Linux (e.g., Fedora KDE), attempt to get the native localized path
    if (osName.contains("linux")) {
        try {
            val process = Runtime.getRuntime().exec(arrayOf("xdg-user-dir", "DOWNLOAD"))
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val xdgPath = reader.readLine()
            if (!xdgPath.isNullOrBlank()) {
                return xdgPath
            }
        } catch (e: Exception) {
            // Silently ignore and fallback if the command fails
        }
    }

    // 2. Manual fallback: Check if the Spanish directory physically exists
    val spanishDownloads = File("$userHome/Descargas")
    if (spanishDownloads.exists() && spanishDownloads.isDirectory) {
        return spanishDownloads.absolutePath
    }

    // 3. Default path for macOS/Windows or English setups
    return "$userHome/Downloads"
}

actual fun savePdfFile(fileName: String, bytes: ByteArray): String {
    val downloadDir = File(getDownloadsDirectory())
    if (!downloadDir.exists()) downloadDir.mkdirs()
    print(downloadDir)
    val targetFile = File(downloadDir, fileName)
    targetFile.writeBytes(bytes)
    return targetFile.absolutePath
}