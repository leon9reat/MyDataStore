package com.medialink.mydatastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context) {
    private val appContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    companion object {
        val KEY_BOOKMARK = preferencesKey<String>("key_bookmark")
    }

    init {
        dataStore = appContext.createDataStore(
            name = "app_preference"
        )
    }

    val bookmark: Flow<String?>
        get() {
            return dataStore.data.map {preference ->
                preference[KEY_BOOKMARK]
            }
        }

    suspend fun saveBookmark(bookmark: String) {
        dataStore.edit { preference ->
            preference[KEY_BOOKMARK] = bookmark
        }
    }
}