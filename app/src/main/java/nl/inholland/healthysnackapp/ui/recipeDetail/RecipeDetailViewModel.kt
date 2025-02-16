package nl.inholland.healthysnackapp.ui.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.data.repositories.FavoriteRecipesRepository
import nl.inholland.healthysnackapp.data.services.RecipeService
import nl.inholland.healthysnackapp.models.Recipe
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeService: RecipeService,
    private val favoriteRecipesRepository: FavoriteRecipesRepository
) : ViewModel() {

    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe

    private val _favoriteRecipes = MutableStateFlow<Set<String>>(emptySet())
    val favoriteRecipes: StateFlow<Set<String>> = _favoriteRecipes

    init {
        viewModelScope.launch {
            favoriteRecipesRepository.favoriteRecipes.collect { savedFavorites ->
                _favoriteRecipes.value = savedFavorites
            }
        }
    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            // Assuming recipe names are unique
            _recipe.value = recipeService.getRecipe(recipeId)
        }
    }

    fun toggleFavorite(recipeId: String) {
        viewModelScope.launch {
            favoriteRecipesRepository.toggleFavoriteRecipe(recipeId)
        }
    }

    fun isRecipeFavorited(recipeId: String): Boolean {
        return _favoriteRecipes.value.contains(recipeId)
    }
}