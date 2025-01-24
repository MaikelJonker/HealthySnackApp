package nl.inholland.healthysnackapp.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.minus
import kotlin.collections.plus

val Context.recipeDataStore by preferencesDataStore(name = "favorite_recipes")

class FavoriteRecipesRepository(private val context: Context) {

    private val favoritesKey = stringSetPreferencesKey("favorite_recipe_ids")

    val favouritesFlow: Flow<Set<String>> = context.recipeDataStore.data
        .map { preferences ->
            preferences[favoritesKey] ?: emptySet()
        }

    suspend fun toggleFavorite(recipeId: String) {
        context.recipeDataStore.edit { preferences ->
            val currentFavorites = preferences[favoritesKey] ?: emptySet()
            if (currentFavorites.contains(recipeId)) {
                preferences[favoritesKey] = currentFavorites - recipeId
            } else {
                preferences[favoritesKey] = currentFavorites + recipeId
            }
        }
    }
}