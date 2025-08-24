package side.dnd.feature.home.state

import kotlinx.serialization.Serializable

@Serializable
data class Store(
    val name: String,
    val address: String,
    val price: Int,
    val distance: Int,
    val image: String,
    val type: StoreType,
    val menu: String,
    val latLng: LatLng
) {
    companion object {
        val DEFAULT = Store(
            name = "카페 테인",
            type = StoreType.CAFE,
            distance = 202,
            address = "서울시 광진구 자양동",
            price = 2500,
            image = "https://picsum.photos/200",
            menu = "카페 라떼",
            latLng = LatLng(37.5665, 126.9780)
        )
    }
}

@Serializable
data class LatLng(val latitude: Double, val longitude: Double) {
    fun toNaverLatLng(): com.naver.maps.geometry.LatLng {
        return com.naver.maps.geometry.LatLng(latitude, longitude)
    }
}

enum class StoreType(val display: String, val category: List<String>) {
    CAFE("카페", listOf("아메리카노"," 라떼", "카푸치노","에스프레소", "콜드브루")),
    RESTAURANT("음식점", listOf("국밥", "밀면","만두","돈까스","칼국수"));

    companion object {
        fun getStoreTypeByOrdinal(ordinal: Int): StoreType {
            return entries.first { it.ordinal == ordinal }
        }
    }
}