package nl.inholland.healthysnackapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import nl.inholland.healthysnackapp.models.Snack

@Composable
fun HomePage(viewModel: HomeViewModel) {
    Column() {
        HeaderSection()
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(contentPadding = PaddingValues(20.dp)) {
            item {
                TabSection()
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                SnackGrid(viewModel)
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)) {
        Text(
            text = "Goedemorgen!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        TextButton(onClick = { /* TODO: Navigate to account creation */ }) {
            Text(
                text = "Creëer een account om je app te personaliseren.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = "",
            onValueChange = { /* TODO: Handle search input */ },
            placeholder = { Text("Zoek op snack of ingrediënt") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            singleLine = true
        )
    }
}

@Composable
fun TabSection() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        listOf("Ontbijt", "Lunch", "Snack", "Avondeten").forEach { tab ->
            Button(
                onClick = { /* TODO: Handle tab click */ },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                )
            ) {
                Text(
                    text = tab,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun SnackGrid(viewModel: HomeViewModel) {
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
                SnackCard(snack = snack, color = color, subColor = subColor, modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun SnackCard(snack: Snack, color: Color, subColor: Color, modifier: Modifier) {
    Card(
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