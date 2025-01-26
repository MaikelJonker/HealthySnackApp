package nl.inholland.healthysnackapp.ui.shoppingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.data.repositories.ShoppingListRepository
import nl.inholland.healthysnackapp.data.services.ProductService
import nl.inholland.healthysnackapp.models.Product
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
    private val productService: ProductService
) : ViewModel() {

    private val _shoppingList = MutableStateFlow<List<ProductWithQuantity>>(emptyList())
    val shoppingList: StateFlow<List<ProductWithQuantity>> = _shoppingList

    init {
        loadShoppingList()
    }

    private fun loadShoppingList() {
        viewModelScope.launch {
            val shoppingListItems = shoppingListRepository.getShoppingList()
            val detailedList = shoppingListItems.map { item ->
                val product = productService.getProductByBarCode(item.barcode)
                ProductWithQuantity(product, item.quantity)
            }
            _shoppingList.value = detailedList
        }
    }

    fun addProductToShoppingList(barcode: String) {
        shoppingListRepository.addProduct(barcode)
        loadShoppingList()
    }

    fun removeProductFromShoppingList(barcode: String) {
        shoppingListRepository.removeProduct(barcode)
        loadShoppingList()
    }
}

data class ProductWithQuantity(
    val product: Product,
    val quantity: Int
)