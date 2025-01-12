package nl.inholland.healthysnackapp.ui.recipeDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RecipeRating(
    viewModel: RecipeDetailViewModel,
    recipeId: Int
){
    Column(){
        Text("Rate this recipe")
    }
}