package nl.inholland.healthysnackapp.ui.shoppingList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.inholland.healthysnackapp.ui.cells.SearchBar

@Composable
fun ShoppingListPage(viewModel: ShoppingListViewModel) {

    val shoppingList by viewModel.shoppingList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        SearchBar("Boodschappenlijst", "Welk product zoek je?", Modifier.height(100.dp))
        ShoppingList(shoppingList, viewModel)
    }
}

@Composable
fun ShoppingList(shoppingList: List<ProductWithQuantity>, viewModel: ShoppingListViewModel){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(shoppingList) { item ->
            ShoppingListItem(viewModel, item)
        }
    }
}

@Composable
fun ShoppingListItem(viewModel: ShoppingListViewModel, item: ProductWithQuantity) {
    var isChecked by remember { mutableStateOf(false) } // Local checkbox state

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.onPrimary,
                RoundedCornerShape(12.dp)
            )
            .padding( vertical = 16.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.width(6.dp))

        // Product Image
        AsyncImage(
            model = item.product.imageUrl,
            contentDescription = "Product Image",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.product.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Aantal: ${item.quantity}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Row {
            IconButton(onClick = { viewModel.addProductToShoppingList(item.product.barcode) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase Quantity",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            TextButton(onClick = { viewModel.removeProductFromShoppingList(item.product.barcode) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Decrease Quantity",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
