package nl.inholland.healthysnackapp.models.responsebodies

import nl.inholland.healthysnackapp.models.Ingredient

data class RecipeResponse(
    val id: Int,
    val name: String,
    val preparation_time: String,
    val calories: String,
    val image_url: String,
    val cost_effectiveness: Int,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val halal: Boolean,
    val ingredients: List<Ingredient>,
    val necessities: List<String>,
    val steps: List<Step>,
    val video_url: String
)

data class Ingredient(
    val amount: String,
    val name: String
)

data class Step(
    val step_number: Int,
    val title: String,
    val text: String
)