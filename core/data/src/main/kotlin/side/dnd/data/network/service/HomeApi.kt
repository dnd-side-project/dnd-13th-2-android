package side.dnd.data.network.service

import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("stores/search")
    suspend fun getStores(
        @Query("menuName") menuName: String,
        @Query("category") category: String,
        @Query("userLat") lat: Double,
        @Query("userLng") lng: Double,
        @Query("sort") sort: String,
        @Query("southWestLat") westLat: Double,
        @Query("southWestLng") westLng: Double,
        @Query("northEastLat") eastLat: Double,
        @Query("northEastLng") eastLng: Double,
    ): List<GetStoresResponse>
}

data class GetStoresResponse(
    val storeId: Int,
    val storeName: String,
    val distance: Int,
    val menuName: String,
    val price: Int,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val imgUrl: String? = null,
    val category: String? = null,
)

data class GetStoresRequest(
    val menuName: String,
    val category: String,
    val lat: Double,
    val lng: Double,
    val westLat: Double,
    val westLng: Double,
    val eastLat: Double,
    val eastLng: Double,
)