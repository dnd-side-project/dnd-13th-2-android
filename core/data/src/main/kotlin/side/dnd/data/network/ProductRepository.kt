package side.dnd.data.network

import side.dnd.data.network.service.ProductApi
import side.dnd.data.network.service.SearchProductResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApi: ProductApi,
) {

    suspend fun searchProduct(productName: String): List<SearchProductResponse> = productApi.getProduct(productName)
}