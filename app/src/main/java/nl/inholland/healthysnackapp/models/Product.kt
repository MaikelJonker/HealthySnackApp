package nl.inholland.healthysnackapp.models

data class Nutrient(
    val name: String,
    val amount: String
)

data class Product(
    val name: String,
    val barcode: String,
    val allergies: String,
    val nutrients: List<Nutrient>,
    val ingredients: List <Ingredient>,
    val categories: List<String>,
    val isVegan: Boolean,
    val isVegetarian: Boolean,
    val nutriScore: String,
    val weight: String,
    val imageUrl: String
)
