package side.dnd.feature.home.home

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable
import androidx.navigation.NavType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.state.LatLng
import side.dnd.feature.home.state.Store
import side.dnd.feature.home.store.SortType
import kotlin.reflect.typeOf

@Stable
data class HomeUiState(
    val searchWord: TextFieldState,
    val stores: ImmutableList<Store>,
    val isLocationTracking: Boolean,
    val sortType: SortType,
) {
    companion object {
        val Empty = HomeUiState(
            searchWord = TextFieldState(""),
            stores = persistentListOf(),
            isLocationTracking = false,
            sortType = SortType.PRICE,
        )
    }
}

@Serializable
@Parcelize
data class UserMapState(
    val currentLocation: LatLng,
    val southWestLimit: LatLng,
    val northEastLimit: LatLng,
) : Parcelable {
    companion object {
        val EMPTY = UserMapState(
            currentLocation = LatLng.EMPTY,
            southWestLimit = LatLng.EMPTY,
            northEastLimit = LatLng.EMPTY,
        )

        val navType = mapOf(typeOf<UserMapState>() to UserMapStateNavType)
    }
}

sealed class HomeEvent {
    data class OnLocationTracking(val isLocationTracking: Boolean) : HomeEvent()
    data object OnSearch : HomeEvent()
    data object NavigateToStore : HomeEvent()
    data class OnChangedUserMapState(val new: UserMapState) : HomeEvent()
    data class RequestStoresBySortType(val sortType: SortType) : HomeEvent()
}

sealed class HomeSideEffect {
    data class Navigate(val action: HomeNavigationAction) : HomeSideEffect()
}

object UserMapStateNavType : NavType<UserMapState>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): UserMapState? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            bundle.getParcelable(key, UserMapState::class.java)
        else
            bundle.getParcelable(key)

    }

    override fun parseValue(value: String): UserMapState {
        return Json.decodeFromString<UserMapState>(value)
    }

    override fun serializeAsValue(value: UserMapState): String {
        return Json.encodeToString(UserMapState.serializer(), value)
    }

    override fun put(bundle: Bundle, key: String, value: UserMapState) {
        bundle.putParcelable(key, value)
    }
}