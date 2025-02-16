package nl.inholland.healthysnackapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.data.services.RecipeService
import nl.inholland.healthysnackapp.models.Recipe
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: RecipeService
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            _recipes.value = service.getAllRecipes()
        }
    }
}
