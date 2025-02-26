package nl.inholland.healthysnackapp.ui.recipeDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import nl.inholland.healthysnackapp.models.Recipe

@Composable
fun RecipeRating(
    viewModel: RecipeDetailViewModel,
    recipeId: Int,
    onBackClick: () -> Unit
) {
    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }

    val recipe = viewModel.recipe.collectAsState().value
    var userRating by remember { mutableIntStateOf(0) }

    Column {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Recept voltooid!",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(3f)
                )

                Spacer(modifier = Modifier.weight(2f))
            }
            Spacer(Modifier.height(60.dp))
            Text(
                text = "Hoe heb je dit recept ervaren?",
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            StarRating(rating = userRating, onRatingChanged = { userRating = it })
            Spacer(Modifier.height(100.dp))
        }
        if(recipe!= null) {
            RatingPageContent(recipe)
        }
        else{
            CircularProgressIndicator()
        }
    }
}

@Composable
fun RatingPageContent(recipe: Recipe){

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = "Snack Image",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = "Ben je enthousiast geworden over dit recept?"
        )
        Row(modifier = Modifier
            .clickable{ shareRecipeLink(context, recipe) }
            .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "Share",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = "Deel het nu met je vrienden en familie!",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun StarRating(rating: Int, onRatingChanged: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            IconButton(
                onClick = { onRatingChanged(i) }
            ) {
                Icon(
                    imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Rate $i stars",
                    tint = if (i <= rating) Color(0xFFFFD700) else MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}


