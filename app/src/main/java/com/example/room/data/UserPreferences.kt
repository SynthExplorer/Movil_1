package com.example.room.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {
    companion object {
        val NAME = stringPreferencesKey("name")
        val LASTNAME = stringPreferencesKey("lastname")
        val CC = stringPreferencesKey("cc")
        val EMAIL = stringPreferencesKey("email")
    }

    suspend fun saveUser(name: String, lastname: String, cc: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[NAME] = name
            prefs[LASTNAME] = lastname
            prefs[CC] = cc
            prefs[EMAIL] = email
        }
    }

    val userData: Flow<Map<String, String>> = context.dataStore.data.map { prefs ->
        mapOf(
            "name" to (prefs[NAME] ?: ""),
            "lastname" to (prefs[LASTNAME] ?: ""),
            "cc" to (prefs[CC] ?: ""),
            "email" to (prefs[EMAIL] ?: "")
        )
    }
}
