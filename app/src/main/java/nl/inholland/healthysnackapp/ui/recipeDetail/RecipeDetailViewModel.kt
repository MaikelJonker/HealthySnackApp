package nl.inholland.healthysnackapp.ui.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.data.services.SnackService
import nl.inholland.healthysnackapp.models.Snack
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val snackService: SnackService
) : ViewModel() {

    private val _recipe = MutableStateFlow<Snack?>(null)
    val recipe: StateFlow<Snack?> = _recipe

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            // Assuming recipe names are unique
            _recipe.value = snackService.getAllSnacks().find { it.id == recipeId }
        }
    }
}