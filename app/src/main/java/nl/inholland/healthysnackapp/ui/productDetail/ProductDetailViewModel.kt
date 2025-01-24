package nl.inholland.healthysnackapp.ui.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.data.repositories.FavoriteProductsRepository
import nl.inholland.healthysnackapp.data.repositories.ShoppingListRepository
import nl.inholland.healthysnackapp.data.services.ProductService
import nl.inholland.healthysnackapp.models.Product
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productService: ProductService,
    private val favoriteProductsRepository: FavoriteProductsRepository,
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _productQuantity = MutableStateFlow(0)
    val productQuantity: StateFlow<Int> = _productQuantity

    fun getProduct(barcode: String) {
        viewModelScope.launch {
            try {
                _product.value = productService.getProductByBarCode(barcode)
                updateProductQuantity(barcode)
            } catch (e: Exception) {
                _product.value = null
            }
        }
    }

    fun checkIfFavorite(barcode: String) {
        viewModelScope.launch {
            val isFavoriteInRepo = favoriteProductsRepository.isFavorite(barcode)
            _isFavorite.value = isFavoriteInRepo
        }
    }

    fun toggleFavorite(barcode: String) {
        viewModelScope.launch {
            val newFavoriteState = !_isFavorite.value
            favoriteProductsRepository.setFavorite(barcode, newFavoriteState)
            _isFavorite.value = newFavoriteState
        }
    }

    fun addProductToShoppingList(barcode: String) {
        shoppingListRepository.addProduct(barcode)
        updateProductQuantity(barcode)
    }

    private fun updateProductQuantity(barcode: String) {
        viewModelScope.launch {
            val shoppingList = shoppingListRepository.getShoppingList()
            val quantity = shoppingList.find { it.barcode == barcode }?.quantity ?: 0
            _productQuantity.value = quantity
        }
    }

    fun removeProductFromShoppingList(barcode: String) {
        shoppingListRepository.removeProduct(barcode)
        updateProductQuantity(barcode)
    }
}

