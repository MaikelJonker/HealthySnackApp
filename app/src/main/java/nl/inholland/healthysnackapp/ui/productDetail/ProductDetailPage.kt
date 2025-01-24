package nl.inholland.healthysnackapp.ui.productDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
                    if (productData.isVegan || productData.isVegetarian) {
                        ProductTags(productData)
                        Spacer(modifier = Modifier.width(30.dp))
                    }
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
                    text = "Momenteel $productQuantity in de boodschappenlijst",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(8.dp))

                if (productQuantity > 0) {
                    // Split button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.Transparent)
                            .align(Alignment.CenterHorizontally)
                            .border(1.dp, Color.Gray, RoundedCornerShape(20.dp))
                    ) {
                        // Green button: Add extra item
                        Box(
                            modifier = Modifier
                                .weight(4f)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 20.dp,
                                        bottomStart = 20.dp
                                    )
                                )
                                .background(Color(0xFF4CAF50)) // Green color
                                .clickable {
                                    viewModel.addProductToShoppingList(productData.barcode)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Add Extra Item",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        // Red button: Remove from shopping list
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 20.dp,
                                        bottomEnd = 20.dp
                                    )
                                )
                                .background(Color(0xFFF44336)) // Red color
                                .clickable {
                                    viewModel.removeProductFromShoppingList(productData.barcode)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove",
                                tint = Color.White
                            )
                        }
                    }
                } else {
                    // Single button: Add to shopping list
                    Button(
                        onClick = { viewModel.addProductToShoppingList(productData.barcode) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.buttonElevation(20.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Voeg toe aan boodschappenlijst",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
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
fun ProductTags(productData: Product){
    Column {
        if (productData.isVegan) {
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

        if (productData.isVegetarian) {
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
    }
}

@Composable
fun DetailedProductInformation(productData: Product){
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
            text = productData.allergies,
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
        NutritionalTable(nutrients = productData.nutrients)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = productData.ingredients.joinToString(", ") { it.name },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}