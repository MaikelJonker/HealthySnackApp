package nl.inholland.healthysnackapp.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Snack(
    val id: Int,
    val name: String,
    @JsonProperty("preparation_time") val preparationTime: String,
    val calories: String,
    @JsonProperty("image_url") val imageUrl: String,
    @JsonProperty("cost_effectiveness") val costEffectiveness: Int,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val halal: Boolean,
    val ingredients: List<Ingredient>,
    val neccesities: List<String>,
    val steps: List<Step>,
    @JsonProperty("video_url") val videoUrl: String
)

data class Step(
    @JsonProperty("step_number") val stepNumber: Int,
    val title: String,
    val text: String
)