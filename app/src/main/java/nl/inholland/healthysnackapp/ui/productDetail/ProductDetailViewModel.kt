package nl.inholland.healthysnackapp.ui.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.data.services.ProductService
import nl.inholland.healthysnackapp.models.Product
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productService: ProductService
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product

    fun getProduct(barcode: String){
        viewModelScope.launch {
            try {
                _product.value = productService.getProductByBarCode(barcode)
            }
            catch(e: Exception){
                _product.value = null
            }
        }
    }
}