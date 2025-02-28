package nl.inholland.healthysnackapp.data.services

import android.util.Log
import nl.inholland.healthysnackapp.data.modules.Api
import nl.inholland.healthysnackapp.data.repositories.ApiRepository
import nl.inholland.healthysnackapp.models.Recipe
import nl.inholland.healthysnackapp.models.mappers.RecipeMapper
import javax.inject.Inject

class RecipeService @Inject constructor(
    @Api private val apiRepository: ApiRepository,
    private val recipeMapper: RecipeMapper
) {

    suspend fun getAllRecipes(): List<Recipe> {
        val recipeListResponse = apiRepository.getRecipes()
        return recipeMapper.mapList(recipeListResponse)
    }

    suspend fun getRecipe(recipeId: Int): Recipe {
        return getAllRecipes().find { recipe -> recipe.id == recipeId }!!
    }
}