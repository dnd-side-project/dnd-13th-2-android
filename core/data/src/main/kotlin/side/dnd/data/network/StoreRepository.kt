package side.dnd.data.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import side.dnd.data.network.service.GetStoresRequest
import side.dnd.data.network.service.GetStoresResponse
import side.dnd.data.network.service.HomeApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(
    private val homeApi: HomeApi,
) {
    val homeCache: StateFlow<HomeCache> field: MutableStateFlow<HomeCache> = MutableStateFlow(
        HomeCache(
            stores = null,
            searchWord = "",
        )
    )

    val sortType: StateFlow<String> field: MutableStateFlow<String> = MutableStateFlow("PRICE")

    suspend fun getStores(request: GetStoresRequest) {
        val stores = homeApi.getStores(
            menuName = request.menuName,
            category = request.category,
            lat = request.lat,
            lng = request.lng,
            sort = sortType.value,
            westLat = request.westLat,
            westLng = request.westLng,
            eastLat = request.eastLat,
            eastLng = request.eastLng,
        )

        homeCache.value = HomeCache(
            stores = stores.map { store ->
                store.copy(category = request.category)
            },
            searchWord = request.menuName
        )
    }

    fun updateSortType(sortType: String) {
        this.sortType.value = sortType
    }

    data class HomeCache(
        val stores: List<GetStoresResponse>?,
        val searchWord: String,
    )
}