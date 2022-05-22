package com.thrifthunter.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoriesPreference private constructor(private val dataStore: DataStore<Preferences>){

    fun getStories(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[NAME] ?:"",
                preferences[EMAIL] ?:"",
                preferences[PASSWORD] ?:"",
                preferences[STATUS] ?: false,
                preferences[TOKEN] ?:""
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[NAME] = user.name
            preferences[EMAIL] = user.email
            preferences[PASSWORD] = user.password
            preferences[STATUS] = user.status
        }
    }

    suspend fun login(token: String) {
        dataStore.edit { preferences ->
            preferences[STATUS] = true
            preferences[TOKEN] = token
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[STATUS] = false
            preferences[TOKEN] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: StoriesPreference? = null

        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
        private val PASSWORD = stringPreferencesKey("password")
        private val STATUS = booleanPreferencesKey("state")
        private val TOKEN = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): StoriesPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = StoriesPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
