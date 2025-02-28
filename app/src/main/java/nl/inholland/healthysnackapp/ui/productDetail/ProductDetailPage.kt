package nl.inholland.healthysnackapp.ui.productDetail

import androidx.annotation.DrawableRes
import nl.inholland.healthysnackapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.inholland.healthysnackapp.models.Nutrient
import nl.inholland.healthysnackapp.models.Product

@Composable
fun ProductDetailPage(
    barcode: String,
    onBackClick: () -> Unit,
    viewModel: ProductDetailViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.getProduct(barcode = barcode)
    }

    val context = LocalContext.current
    val product by viewModel.product.collectAsState()
    val productQuantity by viewModel.productQuantity.collectAsState()

    if (product != null) {
        val productData = product!!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                TopProductBar(onBackClick, viewModel, productData, context)
                Spacer(modifier = Modifier.height(15.dp))

                Row {
                    ProductTags(productData)
                    Spacer(modifier = Modifier.width(30.dp))
                    AsyncImage(
                        model = productData.imageUrl,
                        contentDescription = "Product Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(20.dp))

                // Label for the current quantity
                Text(
                    text = "Momenteel $productQuantity op boodschappenlijst",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { viewModel.addProductToShoppingList(productData.barcode) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(text = "Product toevoegen", color = Color.White)
                    }
                    if (productQuantity > 0) {
                        Button(
                            onClick = { viewModel.removeProductFromShoppingList(productData.barcode) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9F0A00)),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp) // Space between buttons
                        ) {
                            Text(text = "Verwijderen", color = Color.White)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                DetailedProductInformation(productData)
            }
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun NutritionalTable(nutrients: List<Nutrient>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        nutrients.forEach { nutrient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = nutrient.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = nutrient.amount,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Composable
fun ProductTags(product: Product) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        if (product.isVegan) {
            Text(
                text = "Vegan",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            )
        }
        if (product.isVegetarian) {
            Text(
                text = "Vegetarian",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            )
        }
        Image(
            painter = painterResource(id = getNutriScoreDrawable(product.nutriScore)),
            contentDescription = "Nutri-score ${product.nutriScore}",
            modifier = Modifier.size(80.dp)
        )
    }
}

@DrawableRes
fun getNutriScoreDrawable(letter: String): Int {
    return when (letter.lowercase()) {
        "a" -> R.drawable.nutri_a
        "b" -> R.drawable.nutri_b
        "c" -> R.drawable.nutri_c
        "d" -> R.drawable.nutri_d
        "e" -> R.drawable.nutri_e
        else -> R.drawable.nutri_e
    }
}

@Composable
fun DetailedProductInformation(product: Product){
    Column(
        Modifier.padding(16.dp)
    ) {
        Text(
            text = "Allergy Information",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.allergies,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nutritional Values",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        NutritionalTable(nutrients = product.nutrients)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.ingredients.joinToString(", ") { it.name },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}