package nl.inholland.healthysnackapp.ui.recipeDetail

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import nl.inholland.healthysnackapp.models.Recipe

@Composable
fun RecipeDetailPage(
    recipeId: Int,
    viewModel: RecipeDetailViewModel,
    onBackClick: () -> Unit,
    onStartRecipeClick: (id: Int, step: Int) -> Unit
) {
    // Load recipe data
    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)

    }
    val recipe = viewModel.recipe.collectAsState().value
    val context = LocalContext.current

    Column {
        recipe?.let {
            TopBar(
                onBackClick = onBackClick,
                viewModel = viewModel,
                recipeId = recipeId,
                recipe = recipe,
                context = context
            )
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = it.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                    ,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                // Recipe details
                Row(Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                    ) {
                        DetailBadge(it.preparationTime)
                        DetailBadge(if (it.vegetarian) "Vegetarisch" else "Niet vegetarisch")
                        DetailBadge("€".repeat(it.costEffectiveness))
                        DetailBadge(it.calories)
                    }
                    Spacer(Modifier.width(100.dp))
                    AsyncImage(
                        model = recipe.imageUrl,
                        contentDescription = "Snack Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                // Ingredients Section
                item {
                    Text(
                        text = "Ingrediënten",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    it.ingredients.forEach { ingredient ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.secondary,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${ingredient.amount} ${ingredient.name}",
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Necessities Section
                    Text(
                        text = "Benodigdheden",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    it.necessities.forEach { necessity ->
                        Text(
                            text = necessity,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Start Recipe Button
                    Button(
                        onClick = { onStartRecipeClick(recipeId, 1) },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(50.dp),
                                clip = false
                            )
                            .clip(RoundedCornerShape(50.dp)),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                    ) {
                        Text(text = "Start recept")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailBadge(text: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    recipeId: Int,
    onBackClick: () -> Unit,
    viewModel: RecipeDetailViewModel,
    recipe: Recipe,
    context: Context
) {
    val isFavorited by viewModel.favoriteRecipes.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        // Back button
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Back",
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Text(
            text = "Recept",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        // Favorite and Share buttons
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Favorite button
            IconButton(
                onClick = {
                    viewModel.toggleFavorite(recipeId.toString())
                }
            ) {
                Icon(
                    imageVector = if (isFavorited.contains(recipeId.toString())) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    tint = if (isFavorited.contains(recipeId.toString())) Color.Red else MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(25.dp)
                )
            }

            // Share button
            IconButton(onClick = { shareRecipeLink(context, recipe) }) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "Share",
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}

fun shareRecipeLink(context: Context, recipe: Recipe) {
    val recipeLink = "https://www.google.com/search?q=${recipe.name}"
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Check out this product: $recipeLink")
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share Product"))
}
