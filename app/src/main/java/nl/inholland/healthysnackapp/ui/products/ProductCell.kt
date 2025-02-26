package nl.inholland.healthysnackapp.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.inholland.healthysnackapp.models.Product

@Composable
fun ProductCell(product: Product, modifier: Modifier = Modifier, toProductDetail: (String) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        onClick = { toProductDetail(product.barcode) }
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {
            // Image of the product
            AsyncImage(
                model = product.imageUrl,
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Product details
            Column {
                // Product name
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold
                )

                // Product weight
                Text(text = product.weight)

                // Nutri-Score with dynamic color
                val nutriScore = product.nutriScore.uppercase() // Ensure uppercase
                val nutriScoreColor = when (nutriScore) {
                    "A" -> Color(0xFF007F00) // Dark Green
                    "B" -> Color(0xFF60A917) // Light Green
                    "C" -> Color(0xFFFFC107) // Yellow
                    "D" -> Color(0xFFFF5722) // Orange
                    "E" -> Color(0xFFD32F2F) // Red
                    else -> Color.Gray // Default for unknown values
                }
                Text(
                    text = buildAnnotatedString {
                        append("Nutri-score: ") // Regular text
                        withStyle(style = SpanStyle(color = nutriScoreColor, fontWeight = FontWeight.Bold)) {
                            append(nutriScore) // Colored letter
                        }
                    }
                )
            }
        }
    }
}