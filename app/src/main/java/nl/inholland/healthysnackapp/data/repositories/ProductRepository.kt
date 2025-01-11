package nl.inholland.healthysnackapp.data.repositories

import nl.inholland.healthysnackapp.models.responsebodies.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductRepository {
    @GET("v2/product/{barcode}")
    suspend fun getProductByBarcode(
        @Path("barcode") barcode: String
    ): ProductResponse
}