package com.yxhelzvl.umsnh_id.core.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

object JwtUtils {
    fun decodePayload(jwt: String): JsonObject? {
        return try {
            val payload = jwt.split(".")[1]
            val json = String(
                android.util.Base64.decode(payload, android.util.Base64.URL_SAFE),
                Charsets.UTF_8
            )
            Json.parseToJsonElement(json) as JsonObject
        } catch (e: Exception) {
            null
        }
    }

    fun getExpiration(jwt: String): Long? {
        return decodePayload(jwt)?.get("exp")?.jsonPrimitive?.content?.toLongOrNull()
    }

    fun getUserId(jwt: String): Long? {
        val payload = decodePayload(jwt) ?: return null
        return when {
            // Prioridad 1: Buscar "id"
            payload.containsKey("id") ->
                payload["id"]?.jsonPrimitive?.content?.toLongOrNull()

            // Prioridad 2: Buscar "sub" (si algún endpoint lo usa)
            payload.containsKey("sub") ->
                payload["sub"]?.jsonPrimitive?.content?.toLongOrNull()

            else -> null
        }
    }
}