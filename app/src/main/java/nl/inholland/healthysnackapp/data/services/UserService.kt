package nl.inholland.healthysnackapp.data.services

import nl.inholland.healthysnackapp.data.modules.Api
import nl.inholland.healthysnackapp.data.repositories.ApiRepository
import nl.inholland.healthysnackapp.models.User
import javax.inject.Inject

class UserService @Inject constructor(
    @Api private val apiRepository: ApiRepository
) {

    suspend fun getAllUsers(): List<User> {
        return apiRepository.getUsers()
    }

    suspend fun login(email: String, password: String): User? {
        return getAllUsers().find { user -> user.email == email && user.password == password }
    }

    suspend fun getUser(id: Int): User? {
        return getAllUsers().find { user -> user.id == id }
    }
}