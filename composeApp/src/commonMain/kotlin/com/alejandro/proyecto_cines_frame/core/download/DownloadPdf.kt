package com.alejandro.proyecto_cines_frame.core.download

/**
 * Gets the absolute path to the system's Downloads directory.
 * * @return The path as a String.
 */
expect fun getDownloadsDirectory(): String

/**
 * Saves the byte array as a file in the system's Downloads folder.
 *
 * @param fileName The name of the file (e.g., "ticket_123.pdf").
 * @param bytes The file content downloaded from the API.
 * @return The absolute path where the file was saved.
 */
expect fun savePdfFile(fileName: String, bytes: ByteArray): String