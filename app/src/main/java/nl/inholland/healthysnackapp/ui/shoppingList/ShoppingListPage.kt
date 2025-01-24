package nl.inholland.healthysnackapp.ui.shoppingList

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingListPage(viewModel: ShoppingListViewModel) {

    val shoppingList by viewModel.shoppingList.collectAsState()

    Text("Shopping List:", style = MaterialTheme.typography.titleSmall)

    LazyColumn {
        items(shoppingList) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Product: ${item.barcode}, Quantity: ${item.quantity}",
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { viewModel.addProductToShoppingList(item.barcode) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase Quantity"
                    )
                }
                IconButton(onClick = { viewModel.removeProductFromShoppingList(item.barcode) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Decrease Quantity"
                    )
                }
            }
        }
    }
}
