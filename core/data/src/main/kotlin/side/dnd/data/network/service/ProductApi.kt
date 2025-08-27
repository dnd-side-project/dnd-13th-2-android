package side.dnd.data.network.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products/search")
    suspend fun getProduct(@Query("keyword") keyword: String): List<SearchProductResponse>
}

data class SearchProductResponse(
    val productId: Int,
    val name: String,
    val itemName: String,
)