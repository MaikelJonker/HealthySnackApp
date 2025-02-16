package nl.inholland.healthysnackapp.models.mappers

import nl.inholland.healthysnackapp.models.Ingredient
import nl.inholland.healthysnackapp.models.Recipe
import nl.inholland.healthysnackapp.models.Step
import nl.inholland.healthysnackapp.models.responsebodies.RecipeResponse
import javax.inject.Inject

class RecipeMapper @Inject constructor(){

    fun mapList(responseList: List<RecipeResponse>): List<Recipe> {
        return responseList.map { response ->
            Recipe(
                id = response.id,
                name = response.name,
                preparationTime = response.preparation_time,
                calories = response.calories,
                imageUrl = response.image_url,
                costEffectiveness = response.cost_effectiveness,
                vegetarian = response.vegetarian,
                vegan = response.vegan,
                halal = response.halal,
                ingredients = response.ingredients.map { Ingredient(it.name, it.amount) },
                necessities = response.necessities,
                steps = response.steps.map { Step(it.step_number, it.title, it.text) },
                videoUrl = response.video_url
            )
        }
    }
}