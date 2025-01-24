package nl.inholland.healthysnackapp.ui.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.inholland.healthysnackapp.models.Product

@Composable
fun ProductCell(product: Product, modifier: Modifier = Modifier, toProductDetail: (String) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
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
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                // Product name
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold
                    )
                // Product weight
                Text(
                    text = product.weight
                )
                Text(
                    text = product.nutriScore
                )
            }
        }
    }
}