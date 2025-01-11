package nl.inholland.healthysnackapp.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.data.services.ProductService
import nl.inholland.healthysnackapp.models.Product
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productService: ProductService
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product

    fun getProduct(){
        viewModelScope.launch {
            _product.value = productService.getProductByBarCode("3017624010701")
        }
    }
}