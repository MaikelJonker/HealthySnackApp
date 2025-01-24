package nl.inholland.healthysnackapp.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopProductsBar(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = "Producten",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp)) // Rounded corners
                .background(MaterialTheme.colorScheme.secondary), // Cream background color
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search, // Default search icon
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSecondary // Icon color
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Welk product zoek je?",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF3E5940).copy(alpha = 0.6f)
                    )
                )
            }
        }
    }
}