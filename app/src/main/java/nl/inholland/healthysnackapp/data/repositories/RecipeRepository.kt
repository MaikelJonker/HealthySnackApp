package nl.inholland.healthysnackapp.data.repositories

import nl.inholland.healthysnackapp.models.Recipe
import nl.inholland.healthysnackapp.models.responsebodies.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeRepository {
    @GET("recipes.json")
    suspend fun getRecipes(): List<RecipeResponse>
}