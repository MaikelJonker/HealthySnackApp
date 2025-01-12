package nl.inholland.healthysnackapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import nl.inholland.healthysnackapp.models.Snack

@Composable
fun HomePage(viewModel: HomeViewModel, toDetail: (Int) -> Unit) {
    Column() {
        HeaderWithSearchBar()
        LazyColumn(contentPadding = PaddingValues(20.dp)) {
            item{
                // TODO: No backend support for filtering
                FilterSection(activeFilters = 0)
            }
            item {
                TabSection()
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                SnackGrid(viewModel, toDetail)
            }
        }
    }
}

@Composable
fun HeaderWithSearchBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        // Header Text
        Text(
            text = "Goedemorgen!",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Creëer een account om je app te personaliseren.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                textDecoration = TextDecoration.Underline
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
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
                    text = "Zoek op snack of ingrediënt",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF3E5940).copy(alpha = 0.6f)
                    )
                )
            }
        }
    }
}

@Composable
fun FilterSection(activeFilters: Int){
    Row(
         modifier = Modifier.clickable { /* TODO: Missing Backend filtering */ }
    ){
        Icon(
            imageVector = Icons.Outlined.Menu,
            contentDescription = "Filters",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = "Filters",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = activeFilters.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                color = MaterialTheme.colorScheme.primary
            )
                .padding(horizontal = 12.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
    Spacer(Modifier.height(10.dp))
}

@Composable
fun TabSection() {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        listOf("Ontbijt", "Lunch", "Snack", "Avondeten").forEach { tab ->
            item {
                Button(
                    onClick = { /* TODO: Missing Backend quick filtering */ },
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(35.dp),
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = tab,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun SnackGrid(viewModel: HomeViewModel, toDetail: (Int) -> Unit) {
    val snacks by viewModel.snacks.collectAsState()

    // Define the colors and subcolors for the cards
    val colors = listOf(
        Color(0xFF6065BD), // Primary color 1
        Color(0xFF9F3A3B), // Primary color 2
        Color(0xFF038141), // Primary color 3
        Color(0xFF00B4A5)  // Primary color 4
    )

    val subColors = listOf(
        Color(0xFFA1A6FF), // Subcolor 1 for color 1
        Color(0xFFF99C9D), // Subcolor 2 for color 2
        Color(0xFF52E59B), // Subcolor 3 for color 3
        Color(0xFF5AF6E9)  // Subcolor 4 for color 4
    )

    // Split the snacks into chunks of 2 for a 2-column grid
    snacks.chunked(2).forEachIndexed { rowIndex, rowSnacks ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            rowSnacks.forEachIndexed { index, snack ->
                val color = colors[(rowIndex * 2 + index) % colors.size]
                val subColor = subColors[(rowIndex * 2 + index) % subColors.size]
                SnackCard(snack = snack, color = color, subColor = subColor, modifier = Modifier.weight(1f), toDetail)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun SnackCard(snack: Snack, color: Color, subColor: Color, modifier: Modifier, toDetail: (Int) -> Unit) {
    Card(
        onClick = { toDetail(snack.id) },
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(4.dp)
            .height(220.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().weight(1f),
                textAlign = TextAlign.Center,
                text = snack.name,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            AsyncImage(
                model = snack.imageUrl,
                contentDescription = "Snack Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier= Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoText(snack.preparationTime, subColor, Modifier.weight(1f))
                InfoText(snack.calories, subColor, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun InfoText(value: String, subColor: Color, modifier: Modifier) {
    Text(
        text = value,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(subColor, shape = RoundedCornerShape(20.dp))
            .padding(6.dp)
    )
}