package nl.inholland.healthysnackapp.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.minus
import kotlin.collections.plus

val Context.dataStore by preferencesDataStore(name = "recipe_favourites")

class FavoriteRecipesRepository(private val context: Context) {

    private val FAVORITES_KEY = stringSetPreferencesKey("favorite_recipe_ids")

    val favouritesFlow: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[FAVORITES_KEY] ?: emptySet()
        }

    suspend fun toggleFavorite(recipeId: String) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            if (currentFavorites.contains(recipeId)) {
                preferences[FAVORITES_KEY] = currentFavorites - recipeId
            } else {
                preferences[FAVORITES_KEY] = currentFavorites + recipeId
            }
        }
    }
}