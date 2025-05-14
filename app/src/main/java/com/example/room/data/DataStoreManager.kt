package com.example.room.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    val userNameFlow: Flow<String?> = context.dataStore.data
        .map { it[USER_NAME_KEY] }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { it[USER_NAME_KEY] = name }
    }
}
