package com.example.tutorapp.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        val NAME_KEY = stringPreferencesKey("user_name")
        val EMAIL_KEY = stringPreferencesKey("user_email")
        val TOKEN_KEY = stringPreferencesKey("user_token")
    }

    // Lưu
    suspend fun saveUser(name: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[NAME_KEY] = name
            prefs[EMAIL_KEY] = email
        }
    }

    // Đọc
    val userName: Flow<String> = context.dataStore.data.map { it[NAME_KEY] ?: "" }
    val userEmail: Flow<String> = context.dataStore.data.map { it[EMAIL_KEY] ?: "" }

    // Xoá
    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }
}
