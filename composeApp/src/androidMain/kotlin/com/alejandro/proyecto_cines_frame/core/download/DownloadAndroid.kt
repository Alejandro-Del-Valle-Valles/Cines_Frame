package com.alejandro.proyecto_cines_frame.core.download

import android.os.Environment
import java.io.File

actual fun getDownloadsDirectory(): String {
    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
}

actual fun savePdfFile(fileName: String, bytes: ByteArray): String {
    val path = "${getDownloadsDirectory()}/$fileName"
    File(path).writeBytes(bytes)
    return path
}