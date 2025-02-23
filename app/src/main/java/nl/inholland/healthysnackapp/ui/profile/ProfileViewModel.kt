package nl.inholland.healthysnackapp.ui.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nl.inholland.healthysnackapp.data.modules.RecipeApi
import nl.inholland.healthysnackapp.data.repositories.RecipeRepository
import nl.inholland.healthysnackapp.models.User
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @RecipeApi private val recipeRepository: RecipeRepository
) : ViewModel() {

    fun getUser(userId: Int): Flow<User?> = flow {
        emit(recipeRepository.getUsers().find { user-> user.id == userId })
    }.flowOn(Dispatchers.IO)

}