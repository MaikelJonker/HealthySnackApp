package nl.inholland.healthysnackapp.ui.products

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import nl.inholland.healthysnackapp.ui.cells.ProductCell

@Composable
fun ProductList(viewModel: ProductViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getProduct()
    }

    val product by viewModel.product.collectAsState()
    if(product != null) {
        Column() {
            ProductCell(product!!, modifier = Modifier)
        }
    }
}