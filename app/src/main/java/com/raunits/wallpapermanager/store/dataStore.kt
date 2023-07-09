package com.raunits.wallpapermanager.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.StringBuilder

class StoreUtils(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("ctx");
        val CATEGORIES = stringPreferencesKey("categories")
    }

    val getCategories: Flow<String?> = context.dataStore.data.map {
        preferences -> preferences[CATEGORIES] ?: ""
    }

    fun getCachedCategoryWallpapers(category: String): Flow<String?> {
        val key = stringPreferencesKey(category)

        val walls = context.dataStore.data.map {
            prefs -> prefs[key] ?: ""
        }

        return walls
    }

    suspend fun saveCategories(categories: List<String>) {
        val sb =  StringBuilder();
        categories.forEach {
            sb.append(it).append("^")
        }
        sb.deleteCharAt(sb.length-1)

        println("CACHING --- $sb")

        context.dataStore.edit {
            preferences -> preferences[CATEGORIES] = sb.toString()
        }
    }

    suspend fun saveCategoryWallpapers(category: String, wallpapers: List<String>) {
        val sb =  StringBuilder();
        val key = stringPreferencesKey(category)

        wallpapers.forEach {
            sb.append(it).append("^")
        }
        sb.deleteCharAt(sb.length-1)

        println("CACHING $category --- $sb")

        context.dataStore.edit {
            preferences -> preferences[key] = sb.toString()
        }
    }
}