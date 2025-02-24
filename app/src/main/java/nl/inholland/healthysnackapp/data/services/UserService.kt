package nl.inholland.healthysnackapp.data.services

import nl.inholland.healthysnackapp.data.modules.RecipeApi
import nl.inholland.healthysnackapp.data.repositories.RecipeRepository
import nl.inholland.healthysnackapp.models.User
import javax.inject.Inject

class UserService @Inject constructor(
    @RecipeApi private val recipeRepository: RecipeRepository
) {

    suspend fun getAllUsers(): List<User> {
        return recipeRepository.getUsers()
    }

    suspend fun login(email: String, password: String): User? {
        return getAllUsers().find { user -> user.email == email && user.password == password }
    }

    suspend fun getUser(id: Int): User? {
        return getAllUsers().find { user -> user.id == id }
    }
}