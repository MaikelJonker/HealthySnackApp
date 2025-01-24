package nl.inholland.healthysnackapp.ui.shoppingList

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.inholland.healthysnackapp.data.repositories.ShoppingListRepository
import nl.inholland.healthysnackapp.models.ShoppingListItem
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel() {

    private val _shoppingList = MutableStateFlow<List<ShoppingListItem>>(emptyList())
    val shoppingList: StateFlow<List<ShoppingListItem>> = _shoppingList

    init {
        loadShoppingList()
    }

    private fun loadShoppingList() {
        _shoppingList.value = repository.getShoppingList()
    }

    fun addProductToShoppingList(barcode: String) {
        repository.addProduct(barcode)
        loadShoppingList()
    }

    fun removeProductFromShoppingList(barcode: String) {
        repository.removeProduct(barcode)
        loadShoppingList()
    }
}