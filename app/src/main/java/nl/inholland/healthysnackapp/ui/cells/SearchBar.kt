package nl.inholland.healthysnackapp.ui.cells

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(title: String, placeholder: String, modifier: Modifier) {

    var searchText by remember { mutableStateOf("") } // State for search input

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp) // Added padding to avoid screen edges
    ) {
        if(title != "") {
            Text(
                text = title,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp), // Added slight padding for better alignment
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp) // Restrict height for a smaller, compact look
                .clip(RoundedCornerShape(20.dp)) // Rounded corners
                .background(MaterialTheme.colorScheme.secondary) // Background color
                .padding(horizontal = 12.dp), // Inner padding
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search, // Search icon
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSecondary // Icon color
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it }, // Update search text
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF3E5940).copy(alpha = 0.8f)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp) // Avoid text being cut off
                ) { innerTextField ->
                    if (searchText.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF3E5940).copy(alpha = 0.6f)
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    }
}

