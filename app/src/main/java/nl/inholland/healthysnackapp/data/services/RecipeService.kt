package nl.inholland.healthysnackapp.data.services

import nl.inholland.healthysnackapp.data.modules.RecipeApi
import nl.inholland.healthysnackapp.data.repositories.RecipeRepository
import nl.inholland.healthysnackapp.models.Recipe
import nl.inholland.healthysnackapp.models.mappers.RecipeMapper
import javax.inject.Inject

class RecipeService @Inject constructor(
    @RecipeApi private val recipeRepository: RecipeRepository,
    private val recipeMapper: RecipeMapper
) {

    suspend fun getAllRecipes(): List<Recipe> {
        val recipeListResponse = recipeRepository.getRecipes()
        return recipeMapper.mapList(recipeListResponse)
    }

    suspend fun getRecipe(recipeId: Int): Recipe {
        return getAllRecipes().find { recipe -> recipe.id == recipeId }!!
    }
}