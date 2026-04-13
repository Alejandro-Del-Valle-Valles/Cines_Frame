package com.alejandro.proyecto_cines_frame.data.remote.error

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object ApiErrorParser {
    fun parseToMap(raw: String): Map<String, String> =
        runCatching {
            Json.parseToJsonElement(raw).jsonObject
                .mapValues { it.value.jsonPrimitive.content }
        }.getOrDefault(emptyMap())
}