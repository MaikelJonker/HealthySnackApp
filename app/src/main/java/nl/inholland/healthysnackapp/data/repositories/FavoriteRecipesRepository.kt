package nl.inholland.healthysnackapp.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore(name = "favorites_store")

@Singleton
class FavoriteRecipesRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val FAVORITE_RECIPES_KEY = stringSetPreferencesKey("favorite_recipes")

    val favoriteRecipes: Flow<Set<String>> = context.dataStore.data.map { preferences ->
        preferences[FAVORITE_RECIPES_KEY] ?: emptySet()
    }

    suspend fun toggleFavoriteRecipe(recipeId: String) {
        context.dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITE_RECIPES_KEY] ?: emptySet()
            val updatedFavorites = if (currentFavorites.contains(recipeId)) {
                currentFavorites - recipeId // Remove the recipe if it's already favorited
            } else {
                currentFavorites + recipeId // Add the recipe to favorites
            }
            preferences[FAVORITE_RECIPES_KEY] = updatedFavorites
        }
    }
}

