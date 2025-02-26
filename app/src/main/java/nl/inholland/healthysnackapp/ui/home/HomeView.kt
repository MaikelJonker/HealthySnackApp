package nl.inholland.healthysnackapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.data.UserInfo.SessionManager
import nl.inholland.healthysnackapp.data.services.RecipeService
import nl.inholland.healthysnackapp.data.services.UserService
import nl.inholland.healthysnackapp.models.Recipe
import nl.inholland.healthysnackapp.models.User
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: RecipeService,
    private val sessionManager: SessionManager,
    private val userService: UserService
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

    fun getUser(): Flow<User?> = flow{
        if(sessionManager.getUserId() != null) {
            emit(userService.getUser(sessionManager.getUserId()!!))
        }
    }.flowOn(Dispatchers.IO)
}
