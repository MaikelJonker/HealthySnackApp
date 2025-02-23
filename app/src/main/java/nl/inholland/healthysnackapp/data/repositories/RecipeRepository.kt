package nl.inholland.healthysnackapp.data.repositories

import nl.inholland.healthysnackapp.models.User
import nl.inholland.healthysnackapp.models.responsebodies.RecipeResponse
import retrofit2.http.GET

interface RecipeRepository {
    @GET("recipes.json")
    suspend fun getRecipes(): List<RecipeResponse>

    @GET("users.json")
    suspend fun getUsers(): List<User>
}