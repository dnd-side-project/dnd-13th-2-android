package side.dnd.feature.home.home

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable
import side.dnd.feature.home.HomeNavigationAction
import side.dnd.feature.home.state.Store

@Stable
data class HomeUiState(
    val searchWord: String,
    val stores: ImmutableList<Store>,
    val mapControl: MapControl,
    val showSearchContent: Boolean,
) {
    companion object {
        val Empty = HomeUiState(
            searchWord = "",
            stores = persistentListOf(),
            mapControl = MapControl.Empty,
            showSearchContent = false,
        )
    }
}

data class MapControl(
    val isLocationTracking: Boolean,
) {
    companion object {
        val Empty = MapControl(
            isLocationTracking = false
        )
    }
}

sealed class HomeEvent {
    data class OnLocationTracking(val isLocationTracking: Boolean) : HomeEvent()
    data class SwitchContentLayout(val showSearch: Boolean) : HomeEvent()
    data object OnSearch : HomeEvent()
    data object NavigateToStore: HomeEvent()
}

sealed class HomeSideEffect {
    data class Navigate(val action: HomeNavigationAction) : HomeSideEffect()
}