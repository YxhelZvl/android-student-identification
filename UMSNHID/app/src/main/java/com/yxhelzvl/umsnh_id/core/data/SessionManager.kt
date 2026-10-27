package com.yxhelzvl.umsnh_id.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yxhelzvl.umsnh_id.core.utils.JwtUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SessionManager(private val dataStore: DataStore<Preferences>) {
    companion object {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val USER_ID = stringPreferencesKey("user_id")
        val TOKEN_EXPIRATION = longPreferencesKey("token_expiration")
    }

    // Guardar datos después del login
    suspend fun saveSession(token: String, userId: String, expiration: Long) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
            preferences[USER_ID] = userId
            preferences[TOKEN_EXPIRATION] = expiration
        }
    }

    // Obtener token (Flow para observar cambios)
    val authToken: Flow<String?> = dataStore.data
        .map { preferences -> preferences[AUTH_TOKEN] }

    // Verificar si la sesión está activa
    suspend fun isSessionValid(): Boolean {
        return dataStore.data.first().let { preferences ->
            val token = preferences[AUTH_TOKEN] ?: return@let false
            val expiration = JwtUtils.getExpiration(token)?.times(1000) ?: 0
            System.currentTimeMillis() < expiration
        }
    }

    val userId: Flow<String?> = dataStore.data
        .map { preferences -> preferences[USER_ID] }

    suspend fun getCurrentUserId(): String? {
        return dataStore.data.first()[USER_ID]
    }

    // Obtener timestamp de expiración del token
    suspend fun getTokenExpiration(): Long? {
        return dataStore.data.map { it[TOKEN_EXPIRATION] }.first()
    }

    // Convertir timestamp a fecha legible
    fun formatExpirationTime(expiration: Long?): String {
        if (expiration == null) return "No disponible"
        return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            .format(Date(expiration))
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN)
            preferences.remove(USER_ID)
            preferences.remove(TOKEN_EXPIRATION)
        }
    }
}