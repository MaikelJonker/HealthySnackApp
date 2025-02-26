package nl.inholland.healthysnackapp.ui.faq

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.inholland.healthysnackapp.models.FaqEntry
import nl.inholland.healthysnackapp.ui.cells.SearchBar

@Composable
fun FaqPage(viewModel: FaqViewModel) {

    val faq by viewModel.getFaq().collectAsState(initial = null)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SearchBar("Veelgestelde vragen", "Zoek in veelgestelde vragen", Modifier.height(100.dp))
        if(!faq.isNullOrEmpty()) {
            FAQList(faq!!)
        }
        else{
            CircularProgressIndicator()
        }
    }
}

@Composable
fun FAQList(faqEntries: List<FaqEntry>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        faqEntries.forEach { faqEntry ->
            item {
                FAQItem(faqEntry = faqEntry)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun FAQItem(faqEntry: FaqEntry) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.secondary)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = faqEntry.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    tint = Color.Black
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = faqEntry.text,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}