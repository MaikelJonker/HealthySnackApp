package nl.inholland.healthysnackapp.ui.productDetail

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.inholland.healthysnackapp.models.Product

@Composable
fun TopProductBar(onBackClick: () -> Unit, viewModel: ProductDetailViewModel, product: Product, context: Context){

    LaunchedEffect(product.barcode) {
        viewModel.checkIfFavorite(product.barcode)
    }

    val isFavorite by viewModel.isFavorite.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackClick() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.weight(2f))

        Text(
            text = "Product info",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(3f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { viewModel.toggleFavorite(product.barcode) }
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                tint = if (isFavorite) Color.Red else Color.White
            )
        }

        IconButton(
            onClick = { shareProductLink(context, product) }
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "Share"
            )
        }
    }
}

fun shareProductLink(context: Context, product: Product) {
    val productLink = "https://www.google.com/search?q=${product.name}"
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Check out this product: $productLink")
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share Product"))
}