package nl.inholland.healthysnackapp.ui.recipeDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.inholland.healthysnackapp.ui.cells.VideoPlayer

@Composable
fun RecipeStep(
    viewModel: RecipeDetailViewModel,
    recipeId: Int,
    recipeStep: Int,
    onBackClick: () -> Unit,
    onNextClick: (id: Int, step: Int) -> Unit,
    onRatingsClick: (id: Int) -> Unit
) {

    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }

    val recipe = viewModel.recipe.collectAsState().value

    Column {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Stap $recipeStep van ${recipe?.steps?.size}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = {
                    if(recipeStep >= (recipe?.steps?.size ?:1)){
                        onRatingsClick(recipeId)
                    }
                    else{
                        onNextClick(recipeId, recipeStep + 1)
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        contentDescription = "Next",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(203.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                VideoPlayer(videoUrl = recipe?.videoUrl ?: "")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
        // Recipe Step Content
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "Stap $recipeStep: " + (recipe?.steps[recipeStep - 1]?.title ?: ""),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.surface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = recipe?.steps[recipeStep - 1]?.text ?: ""
                )
            }
        }
    }
}
