package nl.inholland.healthysnackapp.data.services

import nl.inholland.healthysnackapp.data.modules.ProductApi
import nl.inholland.healthysnackapp.data.repositories.ProductRepository
import nl.inholland.healthysnackapp.models.Product
import nl.inholland.healthysnackapp.models.mappers.ProductMapper
import nl.inholland.healthysnackapp.models.responsebodies.ProductResponse
import javax.inject.Inject

class ProductService @Inject constructor(
    @ProductApi private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) {
    suspend fun getProductByBarCode(barcode: String): Product {
        val productResponse: ProductResponse = productRepository.getProductByBarcode(barcode)
        val product: Product = productMapper.map(productResponse)
        return product
    }
}