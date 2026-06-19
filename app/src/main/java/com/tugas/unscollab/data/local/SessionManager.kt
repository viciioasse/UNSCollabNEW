package com.tugas.unscollab.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore(name = "session")

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val USER_ID = stringPreferencesKey("user_id")
        val EMAIL = stringPreferencesKey("email")
    }

    suspend fun saveSession(userId: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[EMAIL] = email
        }
    }

    fun getSession(): Flow<Pair<String?, String?>> {
        return context.dataStore.data.map { preferences ->
            val userId = preferences[USER_ID]
            val email = preferences[EMAIL]
            Pair(userId, email)
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}