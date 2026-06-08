package com.example.wishlistapp.data


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_prefs")

class ThemeDataStore(private val context: Context) {

    companion object {
        val COLOR_KEY = stringPreferencesKey("selected_color")
        const val DEFAULT_COLOR = "Pink"
    }

    val selectedColor: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[COLOR_KEY] ?: DEFAULT_COLOR
        }

    suspend fun saveColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[COLOR_KEY] = color
        }
    }
}