package nl.inholland.healthysnackapp.data.repositories

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoriteProductsRepository(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("favorite_products", Context.MODE_PRIVATE)

    private val favoritesKey = "favorites"

    // Flow to observe changes (not strictly needed if persisting to SharedPreferences directly)
    private val _favoritesFlow = MutableStateFlow<Set<String>>(loadFavorites())
    val favoritesFlow: StateFlow<Set<String>> = _favoritesFlow

    fun isFavorite(barcode: String): Boolean {
        return loadFavorites().contains(barcode)
    }

    fun setFavorite(barcode: String, isFavorite: Boolean) {
        val currentFavorites = loadFavorites().toMutableSet()
        if (isFavorite) {
            currentFavorites.add(barcode)
        } else {
            currentFavorites.remove(barcode)
        }
        saveFavorites(currentFavorites)
        _favoritesFlow.value = currentFavorites
    }

    private fun loadFavorites(): Set<String> {
        return sharedPreferences.getStringSet(favoritesKey, emptySet()) ?: emptySet()
    }

    private fun saveFavorites(favorites: Set<String>) {
        sharedPreferences.edit()
            .putStringSet(favoritesKey, favorites)
            .apply()
    }
}