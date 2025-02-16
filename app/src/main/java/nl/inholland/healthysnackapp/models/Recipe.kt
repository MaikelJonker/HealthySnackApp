package nl.inholland.healthysnackapp.models

data class Recipe(
    val id: Int,
    val name: String,
    val preparationTime: String,
    val calories: String,
    val imageUrl: String,
    val costEffectiveness: Int,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val halal: Boolean,
    val ingredients: List<Ingredient>,
    val necessities: List<String>,
    val steps: List<Step>,
    val videoUrl: String
)

data class Step(
    val stepNumber: Int,
    val title: String,
    val text: String
)