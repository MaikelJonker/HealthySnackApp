package nl.inholland.healthysnackapp.models.mappers

import nl.inholland.healthysnackapp.models.Ingredient
import nl.inholland.healthysnackapp.models.Nutrient
import nl.inholland.healthysnackapp.models.Product
import nl.inholland.healthysnackapp.models.responsebodies.Nutriments
import nl.inholland.healthysnackapp.models.responsebodies.ProductResponse
import javax.inject.Inject

class ProductMapper @Inject constructor(){
    fun map(response: ProductResponse): Product {
        return Product(
            name = response.product?.product_name!!,
            barcode = response.code!!,
            allergies = response.product.allergens!!,
            nutrients = response.product.nutriments?.toNutrientList() ?: emptyList(),
            ingredients = response.product.ingredientsText.toIngredientsList(),
            categories = response.product.categories?.split(",")?.map { it.trim() } ?: emptyList(),
            isVegan = response.product.ingredientsAnalysisTags?.contains("en:vegan") == true,
            isVegetarian = response.product.ingredientsAnalysisTags?.contains("en:vegetarian") == true,
            nutriScore = response.product.nutriscore_grade ?: "Unknown",
            weight = (response.product.quantity) ?: "Unknown",
            imageUrl = response.product.image_url ?: "No image found",
        )
    }

    private fun Nutriments.toNutrientList(): List<Nutrient> {
        return listOfNotNull(
            this.energy?.let { Nutrient("Energy", "$it kJ") },
            this.sugars?.let { Nutrient("Sugars", "$it g") }
        )
    }

    private fun String?.toIngredientsList(): List<Ingredient> {
        return this?.split(",")?.map {
            Ingredient(name = it.trim(), amount = "10")
        } ?: emptyList()
    }
}